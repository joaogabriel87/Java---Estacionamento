package com.estacionamento.ApiEstacionamento.Parking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {

    ParkingEntity findByName(String name);

    String name(String name);
}
