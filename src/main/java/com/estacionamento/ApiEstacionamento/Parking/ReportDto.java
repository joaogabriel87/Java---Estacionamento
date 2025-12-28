package com.estacionamento.ApiEstacionamento.Parking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReportDto(
        LocalDate date,
        Integer totalRegister,
        BigDecimal totalFaturado,
        Double tempoMedioMinutes
) {
}
