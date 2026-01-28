package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import com.estacionamento.ApiEstacionamento.Users.UserServices;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class VehicleService {
private VehicleRepository repository;
private VehicleMapper mapper;
private final UserServices userServices;
private final VehicleProducer producer;
private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);


public VehicleService(VehicleRepository repository, VehicleMapper mapper, VehicleProducer producer,  UserServices userServices) {
	this.repository = repository;
    this.mapper = mapper;
    this.producer = producer;
    this.userServices = userServices;
}

 public VehicleEntity create(VehicleDto dto) {
    logger.info("VehcileCreateService {}", dto);
    VehicleEntity entity = mapper.toEntity(dto);
    VehicleEntity newEntity = repository.save(entity);
    logger.info("Vehicle created {}", newEntity);
    return newEntity;
 }

 public List<ResponseVehiclePlate> getVehiclePlate(String plate) {
    logger.info("getVehiclePlateService {}", plate);
    VehicleEntity entity = repository.findByPlate(plate);
    if(entity == null){
        logger.error("getVehiclePlateService  não encontrado {}", plate);
        return null;
    }
    logger.info("getVehiclePlateService final {}", entity);
    return mapper.toResponseList(entity);
 }
    @Transactional
    public VehicleEntity linkVehicle(RequestLinkVehicle dto) throws URISyntaxException, IOException, InterruptedException {
        logger.info("linkVehicleService init {}", dto);
        if (dto.plate() == null || dto.plate().isBlank()) {
            logger.warn("linkVehicleService plate não pode ser nula ou vazia");
            throw new IllegalArgumentException("Placa não pode ser vazia");
        }

        if (!userServices.existsByEmail(dto.email())) {
            logger.error("User not found {}", dto.email());
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        VehicleEntity vehicle = repository.findByPlate(dto.plate());

        if (vehicle == null) {
            vehicle = mapper.requestLinkVehicle(dto);
            vehicle = repository.save(vehicle);
            logger.info("Veiculo novo criado {}", vehicle);
        } else {

            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setColor(dto.color());
            vehicle = repository.save(vehicle);
            logger.info("Veiculo atualizado {}", vehicle);
        }

        producer.sendVehicleCreated(dto);
        logger.info("veiculo enviado para api de user {}", dto);

        return vehicle;
    }

}
