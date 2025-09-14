package com.estacionamento.ApiEstacionamento.Dto.ParkingDto;

import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;

import java.time.LocalDate;
import java.util.List;

public record ReportDto(
        LocalDate date,
        Integer totalRegister,
        Double totalFaturado,
        Double tempoMedioMinutes,
        List<ParkingRecordDto> registers
) {
}
