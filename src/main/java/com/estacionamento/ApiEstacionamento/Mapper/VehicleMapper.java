package com.estacionamento.ApiEstacionamento.Mapper;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingRecordDto;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.ResponseVehicle;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleEntity toEntity(VehicleDto dto){
        return new VehicleEntity(
                dto.placa(),
                dto.tipo()
        );
    }

    public VehicleDto toDto(VehicleEntity entity){
        return new VehicleDto(
                entity.getPlate(),
                entity.getType()
        );
    }

    public ResponseVehicle toResponse(VehicleEntity entity){

        return new ResponseVehicle(
                entity.getPlate(),
                entity.getType()
        );
    }
}
