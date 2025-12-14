package com.estacionamento.ApiEstacionamento.Parking;

import com.estacionamento.ApiEstacionamento.Ticket.TicketEntity;
import com.estacionamento.ApiEstacionamento.Vehicle.TypeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingMapper parkingMapper;
    private final ParkingRepository parkingRepository;

    public ParkingEntity createParking(ParkingDto dto){

        ParkingEntity parking = parkingMapper.toParkingEntity(dto);

        return parkingRepository.save(parking);
    }

    public void addTicket(ParkingEntity parking, TicketEntity ticket) {
        List<TicketEntity> t = List.of(ticket);
        parking.setTickets(t);
        ticket.setParking(parking);
    }

    public void removeCapacity(ParkingEntity parking, TypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de veículo não pode ser nulo");
        }

        if (type == TypeEnum.CAR) {
            if (parking.getCapacityCar() <= 0) {
                throw new IllegalStateException("Não há vagas disponíveis para carros");
            }
            parking.setCapacityCar(parking.getCapacityCar() - 1);

        } else if (type == TypeEnum.MOTORCYCLE) {
            if (parking.getCapacityMoto() <= 0) {
                throw new IllegalStateException("Não há vagas disponíveis para motos");
            }
            parking.setCapacityMoto (parking.getCapacityMoto() - 1);
        }
    }

    public void AddCapacity(ParkingEntity parking, TypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de veículo não pode ser nulo");
        }
        if (type == TypeEnum.CAR) {
            if (parking.getCapacityCar() < parking.getCapacityMaxCar()){
            parking.setCapacityCar(parking.getCapacityCar() + 1);
            }
        }
        else if (type == TypeEnum.MOTORCYCLE) {
            if (parking.getCapacityMoto() < parking.getCapacityMaxMoto()){
            parking.setCapacityMoto(parking.getCapacityMoto() + 1);
            }
        }
    }

    public ReportDto report(String local, LocalDateTime date ) {
        ParkingEntity parking = parkingRepository.findByName(local);

        List<TicketEntity> tickets = parking.getTickets().stream().filter(x -> x.getCheckout() != null && x.getCheckout().toLocalDate().isEqual(date.toLocalDate())).toList();

        BigDecimal totalFaturado =  tickets.stream().map(TicketEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Double tempoMedio =  tickets.stream().filter(x -> x.getCheckout()!=null).mapToDouble(x -> Duration.between(x.getCheckin(), x.getCheckout()).toMinutes()).average().orElse(0);

        return new ReportDto(
                date,
                tickets.size(),
                totalFaturado,
                tempoMedio
        );
    }

        public ResponseParkingVaga getVaga(String local){
            ParkingEntity parking = parkingRepository.findByName(local);
            return parkingMapper.toResponseVagas(parking);
        }
}
