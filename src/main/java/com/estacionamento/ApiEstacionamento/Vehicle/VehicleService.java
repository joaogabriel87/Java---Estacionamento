package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Users.UserServices;
import jakarta.transaction.Transactional;
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

public VehicleService(VehicleRepository repository, VehicleMapper mapper, VehicleProducer producer,  UserServices userServices) {
	this.repository = repository;
    this.mapper = mapper;
    this.producer = producer;
    this.userServices = userServices;
}

 public VehicleEntity create(VehicleDto dto) {
        VehicleEntity entity = mapper.toEntity(dto);
        VehicleEntity newEntity = repository.save(entity);
        return newEntity;
 }

 public List<ResponseVehiclePlate> getVehiclePlate(String plate) {
    VehicleEntity entity = repository.findByPlate(plate);
    if(entity == null){
        return null;
    }
    return mapper.toResponseList(entity);
 }
    @Transactional
    public VehicleEntity linkVehicle(RequestLinkVehicle dto) throws URISyntaxException, IOException, InterruptedException {

        if (dto.plate() == null || dto.plate().isBlank()) {
            throw new IllegalArgumentException("Placa não pode ser vazia");
        }

        if (!userServices.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        VehicleEntity vehicle = repository.findByPlate(dto.plate());

        if (vehicle == null) {
            vehicle = mapper.requestLinkVehicle(dto);
            vehicle = repository.save(vehicle);
        } else {

            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setColor(dto.color());
            vehicle = repository.save(vehicle);
        }

        producer.sendVehicleCreated(dto);

        return vehicle;
    }

}
