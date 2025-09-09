package com.estacionamento.ApiEstacionamento.Dto.ParkingDto;

import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;

public record ParkingDto(
    String code,
    Boolean occupied,
    ParkingRecordDto current
) {
}
