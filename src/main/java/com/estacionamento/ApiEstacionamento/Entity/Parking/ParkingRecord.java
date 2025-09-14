package com.estacionamento.ApiEstacionamento.Entity.Parking;

import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Estacionamento_dados")
public class ParkingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "parking_id")
    private ParkingEntity spot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;
    @Column(name = "horario_chegada")
    private LocalDateTime checkin;
    @Column(name = "horario_saida")
    private LocalDateTime checkout;
    @Column(name = "preco")
    private Double price;

    public ParkingRecord(VehicleEntity vehicle, LocalDateTime checkin, Double price) {
        this.vehicle = vehicle;
        this.checkin = checkin;
        this.price = price;
    }

    public ParkingRecord() {

    }


}
