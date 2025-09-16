package com.glo.repository;

import com.glo.entity.SimDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimDetailsRepo extends JpaRepository<SimDetails, Long> {
    Optional<SimDetails> findBySimNumber(String simNumber);
    Optional<SimDetails> findbySimNumberAndServiceNumber(String simNumber,String serviceNumber);
}
