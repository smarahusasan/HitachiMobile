package com.glo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimValidationRequest {
    private String simNumber;
    private String serviceNumber;
}
