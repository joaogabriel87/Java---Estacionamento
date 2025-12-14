package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
@Validated
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @PostMapping("/{localParking}")
    public ResponseEntity<ResponseEnterTicket>entrada(@PathVariable String localParking, @Valid @RequestBody VehicleDto dto){
        TicketEntity ticket = ticketService.entrada(dto, localParking);
        ResponseEnterTicket response = ticketMapper.toResponse(ticket);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/{codeTicket}")
    public ResponseExitTicket saida(@PathVariable Long codeTicket, @Valid @RequestBody String localParking){
        TicketEntity ticket = ticketService.saida(localParking, codeTicket);
        ResponseExitTicket response = ticketMapper.toResponseExit(ticket);
        return ResponseEntity.status(HttpStatus.OK).body(response).getBody();
    }
}
