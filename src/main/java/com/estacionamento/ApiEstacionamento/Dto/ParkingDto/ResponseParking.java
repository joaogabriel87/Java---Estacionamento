package com.estacionamento.ApiEstacionamento.Dto.ParkingDto;

import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;

public record ResponseParking(
        String code,
        Boolean occupied,
        ParkingRecordDto current
) {
}
