package com.estacionamento.ApiEstacionamento.Dto.ParkingDto;

import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;

import java.time.LocalDateTime;

public record ParkingRecordDto(
VehicleDto vehicle,
LocalDateTime checkin,
Double price

) {
}
