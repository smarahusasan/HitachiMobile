package com.glo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequest {
    private String address;
    private String city;
    private String state;
    private String zip;
}
