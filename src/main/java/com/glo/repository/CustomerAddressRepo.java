package com.glo.repository;

import com.glo.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress,Long> {
}
