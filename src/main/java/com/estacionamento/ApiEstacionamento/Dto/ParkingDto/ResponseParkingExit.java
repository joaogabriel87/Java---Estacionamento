package com.estacionamento.ApiEstacionamento.Dto.ParkingDto;

public record ResponseParkingExit(
        String code,
        Boolean occupied,
        ParkingRecordDto current
) {
}
