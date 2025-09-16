package com.glo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private String uniqueIdNumber;

    private LocalDate dateOfBirth;
    private String email;
    private String firstName;
    private String lastName;
    private String idType;

    @ManyToOne
    @JoinColumn(name="customer_address_id")
    private CustomerAddress customerAddress;

    @ManyToOne
    @JoinColumn(name="sim_id")
    private SimDetails simDetails;

    private String state;
}
