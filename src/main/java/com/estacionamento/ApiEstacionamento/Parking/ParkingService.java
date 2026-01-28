package com.estacionamento.ApiEstacionamento.Parking;

import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingMapper parkingMapper;
    private final ParkingRepository parkingRepository;
    private static final Logger logger = LoggerFactory.getLogger(ParkingService.class);


    public ParkingEntity createParking(RequestCreateParking dto){
        logger.info("ParkingService initial");
        ParkingEntity parking = parkingMapper.toParkingEntity(dto);
        logger.info("ParkingService final");
        return parkingRepository.save(parking);
    }

    public void addTicket(ParkingEntity parking, TicketEntity ticket) {
        logger.info("ParkingService addTicket");
        List<TicketEntity> t = List.of(ticket);
        parking.setTickets(t);
        ticket.setParking(parking);
    }

    public void removeCapacity(ParkingEntity parking, TypeEnum type) {
        logger.info("ParkingService removeCapacity");
        if (type == null) {
            logger.error("ParkingService type is null");
            throw new IllegalArgumentException("Tipo de veículo não pode ser nulo");
        }

        if (type == TypeEnum.CAR) {
            logger.info("ParkingService capacity carros finalizado");
            parking.setCapacityCar(parking.getCapacityCar() - 1);

        } else if (type == TypeEnum.MOTORCYCLE) {
            logger.info("ParkingService capacity motorcycle finalizado");
            parking.setCapacityMoto (parking.getCapacityMoto() - 1);
        }
    }

    public void AddCapacity(ParkingEntity parking, TypeEnum type) {
        logger.info("ParkingService addCapacity");
        if (type == null) {
            logger.error("ParkingService type is null");
            throw new IllegalArgumentException("Tipo de veículo não pode ser nulo");
        }
        if (type == TypeEnum.CAR) {
            if (parking.getCapacityCar() < parking.getCapacityMaxCar()){
                logger.info("ParkingService capacity carros finalizado");
                parking.setCapacityCar(parking.getCapacityCar() + 1);
            }else {
                logger.warn("ParkingService capacity carros lotado");
                throw new IllegalArgumentException("Vagas lotadas");
            }
        }
        else if (type == TypeEnum.MOTORCYCLE) {
            if (parking.getCapacityMoto() < parking.getCapacityMaxMoto()){
                logger.info("ParkingService capacity motorcycle finalizado");
            parking.setCapacityMoto(parking.getCapacityMoto() + 1);
            }else {
            logger.warn("ParkingService capacity motorcycle lotado");
            throw new IllegalArgumentException("Vagas lotadas");
        }
        }
    }

    public ReportDto report(String local, LocalDate date ) {
        logger.info("ParkingService report");
        ParkingEntity parking = parkingRepository.findByName(local);
        logger.info("ParkingService report parking {}",parking);

        List<TicketEntity> tickets = parking.getTickets().stream().filter(x -> x.getCheckout() != null && x.getCheckout().toLocalDate().isEqual(date)).toList();
        logger.info("ParkingService report tickets {}",tickets);

        BigDecimal totalFaturado =  tickets.stream().map(TicketEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        logger.info("ParkingService report totalFaturado {}",totalFaturado);
        Double tempoMedio =  tickets.stream().filter(x -> x.getCheckout()!=null).mapToDouble(x -> Duration.between(x.getCheckin(), x.getCheckout()).toMinutes()).average().orElse(0);
        logger.info("ParkingService report tempoMedio {}",tempoMedio);
        return new ReportDto(
                date,
                tickets.size(),
                totalFaturado,
                tempoMedio
        );
    }

    public ResponseParkingVaga getVaga(String local){
        logger.info("ParkingService getVaga");
        ParkingEntity parking = parkingRepository.findByName(local);
        logger.info("ParkingService getVaga {}",parking);
        return parkingMapper.toResponseVagas(parking);
        }
}
