package com.estacionamento.ApiEstacionamento.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleRepository  extends JpaRepository<VehicleEntity, Long> {
    @Query("SELECT p.status FROM VehicleEntity v JOIN v.tickets p WHERE v.plate = :plate")
    Optional<Boolean> isVehicleParkingOccupied(@Param("plate") String plate);

    VehicleEntity findByPlate(String plate);
}
