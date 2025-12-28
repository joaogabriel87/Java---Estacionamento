package com.estacionamento.ApiEstacionamento.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleRepository  extends JpaRepository<VehicleEntity, Long> {


    VehicleEntity findByPlate(String plate);

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM VehicleEntity v JOIN v.tickets p
    WHERE v.plate = :plate AND p.status = 'USO'
   """)
    Optional<Boolean> isVehicleParkingOccupied(@Param("plate") String plate);

}
