package com.estacionamento.ApiEstacionamento.Ticket;

import com.estacionamento.ApiEstacionamento.Vehicle.VehicleDto;
import com.estacionamento.ApiEstacionamento.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Erro.ParkingisNull;
import com.estacionamento.ApiEstacionamento.Erro.VehicleIsOccupied;
import com.estacionamento.ApiEstacionamento.Parking.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import com.estacionamento.ApiEstacionamento.Vehicle.VehicleService;
import com.estacionamento.ApiEstacionamento.Service.codeService;
import com.estacionamento.ApiEstacionamento.Service.priceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final ParkingService parkingService;
    private final TicketRepository ticketRepository;
    private final ParkingRepository parkingRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final TicketMapper ticketMapper;
    private final codeService codeService;
    private final priceService priceService;

    @Transactional
    public TicketEntity entrada(VehicleDto dto, String nomeLocal) {

        boolean occupied = vehicleRepository
                .isVehicleParkingOccupied(dto.placa())
                .orElse(false);

        if (occupied){
            throw  new VehicleIsOccupied();
        }
        VehicleEntity vehicle =  vehicleRepository.findByPlate(dto.placa());

        if (vehicle == null){
            vehicle = vehicleService.create(dto);
        }

        ParkingEntity parking = parkingRepository.findByName(nomeLocal);
        parkingService.removeCapacity(parking, vehicle.getType());


        TicketEntity ticket = ticketMapper.toEntity(vehicle, parking);
        parkingService.addTicket(parking, ticket);

        return ticketRepository.save(ticket);
    }


    @Transactional
    public TicketEntity saida(String local, Long ticketId){

        ParkingEntity parking = parkingRepository.findByName(local);

        if (parking == null) {
            throw new ParkingisNull("O Local: " + local + " Não existe" );
        }

        TicketEntity ticket = ticketRepository.findBycodeTicket(ticketId);

        ticket.setPrice(priceService.priceCheckout(ticket.getVehicle().getType(), ticket.getCheckin(), parking));

        parkingService.AddCapacity(parking, ticket.getVehicle().getType());

        ticket.setStatus(StatusEnum.FINALIZADO);

        return ticketRepository.save(ticket);



    }


}
