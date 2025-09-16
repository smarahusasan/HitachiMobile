package com.glo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdentity {
    @Id
    private String uniqueIdNumber;

    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String email;
    private String state;
}
