package com.glo.repository;

import com.glo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,String> {
    Optional<Customer> findByEmailAndDateOfBirth(String email, LocalDate dateOfBirth);
    List<Customer> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
