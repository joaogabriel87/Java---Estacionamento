package com.estacionamento.ApiEstacionamento.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseExitTicket(Long price, LocalDateTime horario_chegada, LocalDateTime horario_saida, BigDecimal valor_total) {
}
