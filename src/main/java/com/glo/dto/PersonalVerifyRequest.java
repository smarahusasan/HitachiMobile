package com.glo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalVerifyRequest {
    private String firstName;
    private String lastName;
    private String email;
}
