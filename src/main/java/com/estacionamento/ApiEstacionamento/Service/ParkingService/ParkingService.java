package com.estacionamento.ApiEstacionamento.Service.ParkingService;

import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingRecord;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ParkingMapper;
import com.estacionamento.ApiEstacionamento.Repository.ParkingRepository;
import com.estacionamento.ApiEstacionamento.Repository.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Service.VehicleService.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ParkingService {

    ParkingRepository parkingRepository;
    VehicleService vehicleService;
    ParkingMapper parkingMapper;
    VehicleRepository vehicleRepository;

    public ParkingService(ParkingRepository parkingRepository,  VehicleService vehicleService,  ParkingMapper parkingMapper, VehicleRepository vehicleRepository) {
        this.parkingRepository = parkingRepository;
        this.vehicleService = vehicleService;
        this.parkingMapper = parkingMapper;
        this.vehicleRepository = vehicleRepository;
    }

    public ParkingEntity entrada(VehicleDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Veículo inválido");
        }

        boolean occupied = vehicleRepository
                .isVehicleParkingOccupied(dto.placa())
                .orElse(false); // default se não achar
        if (occupied){
            throw  new IllegalArgumentException("Veiculo ja esta com estacionamento ativo");
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
    public void saida(String code){

        ParkingEntity parking = parkingRepository.findByCode(code);

        if (parking == null) {
            throw new EntityNotFoundException("Estacionamento não encontrado para o código: " + code);
        }

        if (!parking.getOccupied()) {
            throw new IllegalStateException("Estacionamento já está finalizado para o código: " + code);
        }

        ParkingRecord current = parking.getCurrent();
        current.setCheckout(LocalDateTime.now());
        current.setPrice(priceCheckout(
                current.getVehicle().getType(),
                current.getCheckin()
        ));

        parking.setOccupied(false);

        parkingRepository.save(parking);
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


        long extraHours = Math.max(0, duration.toHours() - 1);

        return basePrice + (extraHours * extraPrice);
    }

}
