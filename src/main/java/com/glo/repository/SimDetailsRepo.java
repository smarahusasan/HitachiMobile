package com.glo.repository;

import com.glo.entity.SimDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimDetailsRepo extends JpaRepository<SimDetails, Long> {
    Optional<SimDetails> findBySimNumber(String simNumber);
    Optional<SimDetails> findBySimNumberAndServiceNumber(String simNumber,String serviceNumber);
}
