package com.glo.service;

import com.glo.dto.*;
import com.glo.entity.CustomerAddress;

import java.util.List;

public interface CustomerService {
    List<OfferResponse> validateSim(SimValidationRequest request);
    String validateBasicDetails(BasicVerifyRequest request);
    String validatePersonalDetails(PersonalVerifyRequest request);
    CustomerAddress updateAddress(String uniqueId, AddressUpdateRequest request);
    String validateIdProofAndActivate(IdProofRequest request);
}
