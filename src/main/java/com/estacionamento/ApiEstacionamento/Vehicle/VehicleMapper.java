package com.estacionamento.ApiEstacionamento.Vehicle;

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
