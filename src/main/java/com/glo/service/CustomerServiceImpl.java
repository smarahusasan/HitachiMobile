package com.glo.service;

import com.glo.dto.*;
import com.glo.entity.*;
import com.glo.exception.CustomerNotFoundException;
import com.glo.exception.DetailsDoesNotExistException;
import com.glo.exception.InvalidDetailsException;
import com.glo.exception.InvalidEmailException;
import com.glo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.glo.utils.AppConstants.*;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final SimDetailsRepo simDetailsRepo;
    private final SimOffersRepo simOffersRepo;
    private final CustomerRepo customerRepo;
    private final CustomerAddressRepo customerAddressRepo;
    private final CustomerIdentityRepo customerIdentityRepo;

    @Autowired
    public CustomerServiceImpl(SimDetailsRepo simDetailsRepo, SimOffersRepo simOffersRepo, CustomerRepo customerRepo, CustomerAddressRepo customerAddressRepo, CustomerIdentityRepo customerIdentityRepo) {
        this.simDetailsRepo = simDetailsRepo;
        this.simOffersRepo = simOffersRepo;
        this.customerRepo = customerRepo;
        this.customerAddressRepo = customerAddressRepo;
        this.customerIdentityRepo = customerIdentityRepo;
    }

    @Override
    public List<OfferResponse> validateSim(SimValidationRequest request) {
        String simNumber = request.getSimNumber();
        String serviceNumber = request.getServiceNumber();

        if(simNumber==null || !simNumber.matches("\\d{13}")){
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }
        if(serviceNumber==null || !serviceNumber.matches("\\d{10}")){
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }

        Optional<SimDetails> simDetailsOptional = simDetailsRepo.findBySimNumberAndServiceNumber(simNumber,serviceNumber);
        if(simDetailsOptional.isEmpty()){
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");
        }

        SimDetails simDetails = simDetailsOptional.get();
        if(Status.ACTIVE.equals(simDetails.getStatus())){
            throw new InvalidDetailsException("Subscriber Identity Module (SIM)already active");
        }

        List<SimOffers> offers = simOffersRepo.findBySimDetails(simDetails);
        return offers.stream()
                .map(o->new OfferResponse(String.format("%d calls + %d Gb for Rs. %d, Validity %d days.",o.getCallQty(),o.getDataQty(),o.getCost(),o.getDuration())))
                .toList();
    }

    @Override
    public String validateBasicDetails(BasicVerifyRequest request) {
        if (request.getEmail() == null || request.getDate() == null || request.getEmail().isBlank() || request.getDate().isBlank()) {
            throw new InvalidDetailsException("Email/dob value is required");
        }
        if (!EMAIL_REGEX.matcher(request.getEmail()).matches())
            throw new InvalidEmailException("Invalid email");

        LocalDate dob;
        try {
            dob = LocalDate.parse(request.getDate());
        } catch (DateTimeParseException ex) {
            throw new InvalidDetailsException("dob should be in yyyy-mm-dd format");
        }

        var custOpt = customerRepo.findByEmailAndDateOfBirth(request.getEmail(), dob);
        if (custOpt.isEmpty())
            throw new DetailsDoesNotExistException("No request placed for you.");
        return "Basic details validated";
    }

    @Override
    public String validatePersonalDetails(PersonalVerifyRequest request) {
        if (request.getFirstName() == null || request.getLastName() == null)
            throw new InvalidDetailsException("Firstname/Lastname should be a maximum of 15 characters");
        if (!NAME_REGEX.matcher(request.getFirstName()).matches() || !NAME_REGEX.matcher(request.getLastName()).matches()) {
            throw new InvalidDetailsException("Firstname/Lastname should be a maximum of 15 characters");
        }
        var list = customerRepo.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(request.getFirstName(), request.getLastName());
        if (list.isEmpty())
            throw new InvalidDetailsException("No customer found for the provided details");

        var stored = list.getFirst();
        if (!stored.getEmail().equalsIgnoreCase(request.getEmail()))
            throw new InvalidEmailException("Invalid email details!!");
        return "Personal details validated";
    }

    @Override
    @Transactional
    public CustomerAddress updateAddress(String uniqueId, AddressUpdateRequest request) {
        var custOpt = customerRepo.findById(uniqueId);
        if (custOpt.isEmpty())
            throw new CustomerNotFoundException("Customer Not Found message");

        if (request.getAddress() != null && request.getAddress().length() > 25)
            throw new InvalidDetailsException("Address should be maximum of 25 characters");
        if (request.getZip() == null || !request.getZip().matches("\\d{6}"))
            throw new InvalidDetailsException("Pin should be 6 digit number");
        if (!CITY_STATE_REGEX.matcher(request.getCity()).matches() || !CITY_STATE_REGEX.matcher(request.getState()).matches()) {
            throw new InvalidDetailsException("City/State should not contain any special characters except space");
        }

        Customer customer = custOpt.get();
        CustomerAddress addr = customer.getCustomerAddress();
        if (addr == null) addr = new CustomerAddress();

        addr.setAddress(request.getAddress());
        addr.setCity(request.getCity());
        addr.setState(request.getState());
        addr.setZip(request.getZip());

        var saved = customerAddressRepo.save(addr);
        customer.setCustomerAddress(saved);
        customerRepo.save(customer);
        return saved;
    }

    @Override
    @Transactional
    public String validateIdProofAndActivate(IdProofRequest request) {
        if (request.getUniqueIdNumber() == null || !request.getUniqueIdNumber().matches("\\d{16}"))
            throw new InvalidDetailsException("Id should be 16 digits");
        if (request.getFirstName() == null || request.getLastName() == null)
            throw new InvalidDetailsException("Invalid details");

        LocalDate dob;
        try {
            dob = LocalDate.parse(request.getDateOfBirth());
        } catch (DateTimeParseException ex) {
            throw new InvalidDetailsException("Invalid details");
        }

        var idOpt = customerIdentityRepo.findById(request.getUniqueIdNumber());
        if (idOpt.isEmpty())
            throw new CustomerNotFoundException("Customer Not Found message");

        var identity = idOpt.get();
        if (!identity.getFirstName().equalsIgnoreCase(request.getFirstName()) || !identity.getLastName().equalsIgnoreCase(request.getLastName()) || !identity.getDateOfBirth().equals(dob)) {
            throw new InvalidDetailsException("Invalid details");
        }

        var simOpt = simDetailsRepo.findBySimNumber(request.getSimNumber());
        if (simOpt.isEmpty())
            throw new InvalidDetailsException("Invalid details, please check again Subscriber Identity Module (SIM)number/Service number!");

        SimDetails sim = simOpt.get();
        Customer customer=customerRepo.findById(identity.getUniqueIdNumber()).orElse(null);
        assert customer != null;
        if(!Objects.equals(sim.getId(), customer.getSimDetails().getId()))
            throw new InvalidDetailsException("The sim is not in the customer's property");
        sim.setStatus(Status.ACTIVE);
        simDetailsRepo.save(sim);

        return "ID validated and SIM activated";
    }
}
