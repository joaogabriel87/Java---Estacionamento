package com.estacionamento.ApiEstacionamento.Service.ParkingService;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingRecordDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ReportDto;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Erro.ParkingIsOccupiedTrue;
import com.estacionamento.ApiEstacionamento.Erro.ParkingisNull;
import com.estacionamento.ApiEstacionamento.Erro.VehicleIsOccupied;
import com.estacionamento.ApiEstacionamento.Mapper.ParkingMapper;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRecordRepository;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Repository.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Service.VehicleService.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Service
public class ParkingService {

    ParkingRepository parkingRepository;
    VehicleService vehicleService;
    ParkingMapper parkingMapper;
    VehicleRepository vehicleRepository;
    ParkingRecordRepository  parkingRecordRepository;

    public ParkingService(ParkingRepository parkingRepository,  VehicleService vehicleService,  ParkingMapper parkingMapper, VehicleRepository vehicleRepository, ParkingRecordRepository parkingRecordRepository) {
        this.parkingRepository = parkingRepository;
        this.vehicleService = vehicleService;
        this.parkingMapper = parkingMapper;
        this.vehicleRepository = vehicleRepository;
        this.parkingRecordRepository = parkingRecordRepository;
    }

    public ParkingEntity entrada(VehicleDto dto) {

        boolean occupied = vehicleRepository
                .isVehicleParkingOccupied(dto.placa())
                .orElse(false);

        if (occupied){
            throw  new VehicleIsOccupied();
        }
        VehicleEntity vehicle = vehicleService.create(dto);


        ParkingRecord record = new ParkingRecord();
        record.setVehicle(vehicle);
        record.setCheckin(LocalDateTime.now());
        record.setPrice(0.0);

        ParkingEntity parking = new ParkingEntity();
        parking.setCode(CodeParking(dto.tipo()));
        parking.setOccupied(true);
        parking.setCurrent(record);
        parking.getRecords().add(record);

        vehicle.setParking(parking);
        record.setSpot(parking);

        return parkingRepository.save(parking);
    }

    public List<ParkingEntity> allParkings() {
        return parkingRepository.findAll();
    }

    @Transactional
    public ParkingEntity saida(String code){

        ParkingEntity parking = parkingRepository.findByCode(code);

        if (parking == null) {
            throw new ParkingisNull("O Estacionamento: " + code + " Não existe" );
        }

        if (!parking.getOccupied()) {
            throw new ParkingIsOccupiedTrue("Estacionamento já está finalizado para o código: " + code);
        }

        ParkingRecord current = parking.getCurrent();
        current.setCheckout(LocalDateTime.now());
        current.setPrice(priceCheckout(
                current.getVehicle().getType(),
                current.getCheckin()
        ));

        parking.setOccupied(false);

       return parkingRepository.save(parking);
    }

    public ReportDto report(LocalDate date){
        LocalDateTime start =date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<ParkingRecord> registros = parkingRecordRepository.findByCheckinBetween(start, end);

        Double totalFaturado = registros.stream().mapToDouble(x -> x.getPrice() != null ? x.getPrice() : 0).sum();

        Double tempoMedio = registros.stream().filter(x -> x.getCheckout()!=null).
                mapToDouble(x -> Duration.between(x.getCheckin(), x.getCheckout()).toMinutes()).average().orElse(0.0);

        List<ParkingRecordDto> dtos =  registros.stream().
                map(x -> new ParkingRecordDto(
                        new VehicleDto(x.getVehicle().getPlate(),
                                x.getVehicle().getType()),
                        x.getCheckin(),
                        x.getCheckout(),
                        x.getPrice()
                )).toList();

        return new ReportDto(
                date,
                registros.size(),
                totalFaturado,
                tempoMedio,
                dtos
        );
    }

    private String CodeParking(TypeEnum type){
        String code = (type == TypeEnum.CAR ? "C" : "M") + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return code;
    }

    private Double priceCheckout(TypeEnum type, LocalDateTime checkin) {
        double basePrice = (type == TypeEnum.CAR) ? 15.0 : 8.0;
        double extraPrice = (type == TypeEnum.CAR) ? 5.0 : 2.0;

        Duration duration = Duration.between(checkin, LocalDateTime.now());


        if (duration.toMinutes() <= 15) {
            return 0.0;
        }


        long extraHours = Math.max(0, duration.toHours());

        return basePrice + (extraHours * extraPrice);
    }

}
