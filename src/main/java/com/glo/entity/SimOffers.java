package com.glo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int callQty;
    private int cost;
    private int dataQty;
    private int duration;
    private String offerName;

    @ManyToOne
    @JoinColumn(name="sim_id")
    private SimDetails simDetails;
}
