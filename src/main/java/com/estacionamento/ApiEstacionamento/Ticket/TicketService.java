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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

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
        logger.info("entradaService {}", dto);

        boolean occupied = vehicleRepository
                .isVehicleParkingOccupied(dto.placa())
                .orElse(false);

        if (occupied){
            logger.warn("Veiculo ja ocupa vaga");
            throw  new VehicleIsOccupied();
        }
        VehicleEntity vehicle =  vehicleRepository.findByPlate(dto.placa());

        if (vehicle == null){
            vehicle = vehicleService.create(dto);
            logger.info("Veiculo nao encontrado, criado outro {}", vehicle);
        }

        ParkingEntity parking = parkingRepository.findByName(nomeLocal);
        parkingService.removeCapacity(parking, vehicle.getType());
        logger.info(" removendo uma capacidade do estacionamento {}", parking);


        TicketEntity ticket = ticketMapper.toEntity(vehicle, parking);
        parkingService.addTicket(parking, ticket);
        logger.info("Adicinando um ticket no historico do parking {}", ticket);

        return ticketRepository.save(ticket);
    }


    @Transactional
    public TicketEntity saida(String local, Long ticketId){
        logger.info("saidaService {}", local);
        ParkingEntity parking = parkingRepository.findByName(local);

        if (parking == null) {
            logger.error("Parking nao encontrado");
            throw new ParkingisNull("O Local: " + local + " Não existe" );
        }

        TicketEntity ticket = ticketRepository.findBycodeTicket(ticketId);
        logger.info("saidaService : {}", ticket);

        ticket.setPrice(priceService.priceCheckout(ticket.getVehicle().getType(), ticket.getCheckin(), parking));
        logger.info("preço do estacionamento : {}", ticket.getPrice());

        parkingService.AddCapacity(parking, ticket.getVehicle().getType());
        logger.info("adicionando estacionamento do parking {}", ticket);

        ticket.setStatus(StatusEnum.FINALIZADO);
        logger.info("mudando o ticker para finalizado {}", ticket);
        ticket.setCheckout(LocalDateTime.now());
        logger.info("informando o horario do checkout {}", LocalDateTime.now());

        return ticketRepository.save(ticket);

    }


}
