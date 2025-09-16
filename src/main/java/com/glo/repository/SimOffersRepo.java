package com.glo.repository;

import com.glo.entity.SimDetails;
import com.glo.entity.SimOffers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimOffersRepo extends JpaRepository<SimOffers, Long> {
    List<SimOffers> findBySimDetails(SimDetails simDetails);
}
