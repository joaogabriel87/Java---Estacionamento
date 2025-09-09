package com.estacionamento.ApiEstacionamento.Repository;

import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleRepository  extends JpaRepository<VehicleEntity, Long> {
    @Query("SELECT p.occupied FROM VehicleEntity v JOIN v.parking p WHERE v.plate = :plate")
    Optional<Boolean> isVehicleParkingOccupied(@Param("plate") String plate);
}
