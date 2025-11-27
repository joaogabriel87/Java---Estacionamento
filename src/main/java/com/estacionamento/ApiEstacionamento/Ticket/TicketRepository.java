package com.estacionamento.ApiEstacionamento.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {

    TicketEntity findBycodeTicket(Long codeTicket);
}
