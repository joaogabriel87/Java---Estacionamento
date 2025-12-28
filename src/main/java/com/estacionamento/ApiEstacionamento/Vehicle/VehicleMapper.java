package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {

    public VehicleEntity toEntity(VehicleDto dto){
        return new VehicleEntity(
                dto.placa(),
                dto.tipo()
        );
    }


    public List<ResponseVehiclePlate> toResponseList(VehicleEntity entity) {
        return entity.getTickets().stream()
                .map(ticket -> new ResponseVehiclePlate(
                        ticket.getCodeTicket(),
                        entity.getPlate(),
                        ticket.getCheckin(),
                        ticket.getCheckout(),
                        ticket.getPrice(),
                        ticket.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
