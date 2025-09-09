package com.estacionamento.ApiEstacionamento.Entity.Parking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ParkingEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "codigo",unique = true)
    private String code;
    @Column(name = "ocupado")
    private Boolean occupied;
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingRecord> records = new ArrayList<>();

    @OneToOne()
    private ParkingRecord current;

    public ParkingEntity( String code, Boolean occupied,  ParkingRecord current) {
        this.code = code;
        this.occupied = occupied;
        ParkingRecord p = new ParkingRecord();


    }

    public ParkingEntity() {

    }
}
