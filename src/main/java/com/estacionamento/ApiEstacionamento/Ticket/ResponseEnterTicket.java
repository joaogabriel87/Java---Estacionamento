package com.estacionamento.ApiEstacionamento.Ticket;

import java.time.LocalDateTime;

public record ResponseEnterTicket(Long codigo, LocalDateTime horario_chegada) {
}
