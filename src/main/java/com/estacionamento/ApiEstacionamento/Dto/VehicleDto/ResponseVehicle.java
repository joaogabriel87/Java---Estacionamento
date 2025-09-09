package com.estacionamento.ApiEstacionamento.Dto.VehicleDto;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingDto;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;

public record ResponseVehicle(
        String placa,
        TypeEnum tipo
) {
}
