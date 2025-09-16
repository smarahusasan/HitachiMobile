package com.glo.repository;

import com.glo.entity.CustomerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerIdentityRepo extends JpaRepository<CustomerIdentity,String > {
}
