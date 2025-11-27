package com.estacionamento.ApiEstacionamento.Ticket;

import java.time.LocalDateTime;

public record ResponseTicket(Long codigo, LocalDateTime horario_chegada) {
}
