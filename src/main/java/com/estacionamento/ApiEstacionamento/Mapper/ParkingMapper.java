package com.estacionamento.ApiEstacionamento.Mapper;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingRecordDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParking;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParkingExit;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ParkingMapper {

    ParkingRepository parkingRepository;
    public ParkingMapper(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;

    }
    public ParkingDto toDto(ParkingEntity parking) {
        VehicleEntity vehicle = parking.getCurrent().getVehicle();

        VehicleDto dtoVehicle = new VehicleDto(
                vehicle.getPlate(),
                vehicle.getType()
        );

        ParkingRecordDto parkingRecordDto = new ParkingRecordDto(
                dtoVehicle,
                parking.getCurrent().getCheckin(),
                null,
                parking.getCurrent().getPrice()
        );

        return new ParkingDto(
                parking.getCode(),
                parking.getOccupied(),
                parkingRecordDto
        );

    }

    public ParkingEntity toEntity(VehicleDto dto){
        VehicleEntity entityVehicle = new VehicleEntity ();
        entityVehicle.setPlate(dto.placa());
        entityVehicle.setType(dto.tipo());


        ParkingRecord parkingRecord =  new ParkingRecord();
        parkingRecord.setVehicle(entityVehicle);
        parkingRecord.setCheckin(LocalDateTime.now());
        parkingRecord.setPrice(0.0);


        ParkingEntity parking = new ParkingEntity();
        parking.setOccupied(true);
        parking.setCurrent(parkingRecord);

        parkingRecord.setSpot(parking);


        return parking;


    }

    public ResponseParking toResponse(ParkingEntity  entity){
        ParkingRecord record = entity.getCurrent();

        ParkingRecordDto parkingRecordDto = new ParkingRecordDto(
                new VehicleDto(
                        record.getVehicle().getPlate(),
                        record.getVehicle().getType()
                ),
                record.getCheckin(),
                null,
                record.getPrice()
        );

        return new ResponseParking(
                entity.getCode(),
                entity.getOccupied(),
                parkingRecordDto
        );
    }

    public ResponseParkingExit toResponseExit(ParkingEntity entity){
        ParkingEntity parking = parkingRepository.findByCode(entity.getCode());

        VehicleDto vehicleDto = new VehicleDto(
                parking.getCurrent().getVehicle().getPlate(),
                parking.getCurrent().getVehicle().getType()
        );
        ParkingRecordDto current = new ParkingRecordDto(
                vehicleDto,
                parking.getCurrent().getCheckin(),
                parking.getCurrent().getCheckout(),
                parking.getCurrent().getPrice()

        );

        return new ResponseParkingExit(
                entity.getCode(),
                entity.getOccupied(),
                current
        );


    }


}
