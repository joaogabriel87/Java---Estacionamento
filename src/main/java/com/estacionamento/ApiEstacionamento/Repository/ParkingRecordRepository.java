package com.estacionamento.ApiEstacionamento.Repository;

import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingRecordRepository extends JpaRepository<ParkingEntity, Long> {
    @Query("SELECT r FROM ParkingRecord r WHERE r.checkin BETWEEN :start AND :end")
    List<ParkingRecord> findByCheckinBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
