package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Service.codeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketMapper {

    private final codeService codeService;

    public TicketMapper(codeService codeService) {
        this.codeService = codeService;
    }

    public TicketEntity toEntity(VehicleEntity vehicle, ParkingEntity parking) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setCodeTicket(codeService.CodeParking());
        ticketEntity.setVehicle(vehicle);
        ticketEntity.setParking(parking);
        ticketEntity.setCheckin(LocalDateTime.now());
        ticketEntity.setStatus(StatusEnum.USO);

        return ticketEntity;
    }

    public ResponseTicket toResponse(TicketEntity ticketEntity) {
        return new ResponseTicket(
                ticketEntity.getCodeTicket(),
                ticketEntity.getCheckin()
        );
    }

}
