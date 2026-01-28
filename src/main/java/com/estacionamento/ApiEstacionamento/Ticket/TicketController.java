package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
@Validated
@RequiredArgsConstructor
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @PostMapping("/enter/{localParking}")
    public ResponseEntity<ResponseEnterTicket>entrada(@PathVariable String localParking, @Valid @RequestBody VehicleDto dto){
        logger.info("entradaControler {}", localParking);
        TicketEntity ticket = ticketService.entrada(dto, localParking);
        logger.info("ticketControler {}", ticket);
        ResponseEnterTicket response = ticketMapper.toResponse(ticket);
        logger.info("responseControler {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/exit/{codeTicket}/{localParking}")
    public ResponseEntity<ResponseExitTicket> saida(@PathVariable Long codeTicket, @PathVariable String localParking){
        logger.info("saidaControler {}", localParking);
        TicketEntity ticket = ticketService.saida(localParking, codeTicket);
        logger.info("ticketControlerSaida {}", ticket);
        ResponseExitTicket response = ticketMapper.toResponseExit(ticket);
        logger.info("responseControlerSaida {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
