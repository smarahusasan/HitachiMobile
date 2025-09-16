package com.glo.controller;

import com.glo.dto.*;
import com.glo.entity.CustomerAddress;
import com.glo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/sim/validate")
    public ResponseEntity<List<OfferResponse>> validateSim(@RequestBody SimValidationRequest request){
        return ResponseEntity.ok(customerService.validateSim(request));
    }

    @PostMapping("/customer/verify-basic")
    public ResponseEntity<String> verifyBasic(@RequestBody BasicVerifyRequest request){
        return ResponseEntity.ok(customerService.validateBasicDetails(request));
    }

    @PostMapping("/customer/verify-personal")
    public ResponseEntity<String> verifyPersonal(@RequestBody PersonalVerifyRequest request){
        return ResponseEntity.ok(customerService.validatePersonalDetails(request));
    }

    @PutMapping("/customer/{id}/address")
    public ResponseEntity<CustomerAddress> updateAddress(@PathVariable("id") String id, @RequestBody AddressUpdateRequest request){
        return ResponseEntity.ok(customerService.updateAddress(id, request));
    }

    @PostMapping("/customer/validate-id")
    public ResponseEntity<String> validateId(@RequestBody IdProofRequest request){
        return ResponseEntity.ok(customerService.validateIdProofAndActivate(request));
    }
}
