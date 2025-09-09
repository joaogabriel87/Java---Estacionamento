package com.estacionamento.ApiEstacionamento.Repository;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingEntity, Long> {
    ParkingEntity findByCode(String code);

}
