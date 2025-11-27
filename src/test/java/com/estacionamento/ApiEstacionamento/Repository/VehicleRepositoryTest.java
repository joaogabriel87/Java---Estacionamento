//package com.estacionamento.ApiEstacionamento.Repository;
//
//
//import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
//import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
//import com.estacionamento.ApiEstacionamento.Vehicle.VehicleEntity;
//import static org.assertj.core.api.Assertions.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@DataJpaTest
//@ActiveProfiles("test")
//class VehicleRepositoryTest {
//
//    @Autowired
//    ParkingRepository parkingRepository;
//    @Autowired
//    VehicleRepository vehicleRepository;
//
//
//    @Test
//    @DisplayName("return vehicle is occupied true")
//    void isVehicleParkingOccupiedisTrue() {
//        VehicleEntity vehicleEntity = new VehicleEntity("abc123", TypeEnum.MOTORCYCLE);
//        ParkingRecord parkingRecord = new ParkingRecord(vehicleEntity, LocalDateTime.now(), 0.0);
//        ParkingEntity parking = new ParkingEntity("12346", true, parkingRecord);
//
//        parking.setCurrent(parkingRecord);
//        parkingRecord.setSpot(parking);
//        vehicleEntity.setParking(parking);
//
//        this.parkingRepository.save(parking);
//
//        Optional<Boolean> vehicleOccupied = this.vehicleRepository.isVehicleParkingOccupied(vehicleEntity.getPlate());
//
//        assertThat(vehicleOccupied).contains(true);
//    }
//
//    @Test
//    @DisplayName("return vehicle is occupied false")
//    void isVehicleParkingOccupiedisFalse() {
//        VehicleEntity vehicleEntity = new VehicleEntity("abc125", TypeEnum.MOTORCYCLE);
//        ParkingRecord parkingRecord = new ParkingRecord(vehicleEntity, LocalDateTime.now(), 0.0);
//        ParkingEntity parking = new ParkingEntity("12347", false, parkingRecord);
//
//        parking.setCurrent(parkingRecord);
//        parkingRecord.setSpot(parking);
//        vehicleEntity.setParking(parking);
//
//        this.parkingRepository.save(parking);
//
//        Optional<Boolean> vehicleOccupied = this.vehicleRepository.isVehicleParkingOccupied(vehicleEntity.getPlate());
//
//        assertThat(vehicleOccupied).contains(false);
//    }
//
//
//
//}