package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Ticket.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseVehiclePlate(
        Long codeTicket,
        String plate,
        LocalDateTime checkin,
        LocalDateTime checkout,
        BigDecimal price,
        StatusEnum status
) {
}
