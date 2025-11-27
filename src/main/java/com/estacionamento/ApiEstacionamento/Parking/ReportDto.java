package com.estacionamento.ApiEstacionamento.Parking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReportDto(
        LocalDateTime date,
        Integer totalRegister,
        BigDecimal totalFaturado,
        Double tempoMedioMinutes
) {
}
