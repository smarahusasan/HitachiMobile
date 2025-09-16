package com.glo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdProofRequest {
    private String uniqueIdNumber;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String simNumber;
}
