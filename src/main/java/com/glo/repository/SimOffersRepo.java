package com.glo.repository;

import com.glo.entity.SimDetails;
import com.glo.entity.SimOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimOffersRepo extends JpaRepository<SimOffers, Long> {
    List<SimOffers> findBySimDetails(SimDetails simDetails);
}
