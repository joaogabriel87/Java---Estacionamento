package com.estacionamento.ApiEstacionamento.Entity.Vehicle;

import com.estacionamento.ApiEstacionamento.Entity.Clients.ClientsEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa")
    private String plate;
    @Column(name = "marca")
    private String brand;
    @Column(name = "cor")
    private String color;
    @Column(name = "modelo")
    private String model;
    @Column(name="tipo")
    private TypeEnum type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private ClientsEntity  client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    @JsonIgnore
    private ParkingEntity parking;


    public VehicleEntity(String plate, TypeEnum type) {
        this.plate = plate;
        this.type = type;
    }

    public VehicleEntity() {

    }
}
