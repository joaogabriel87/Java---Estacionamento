package com.estacionamento.ApiEstacionamento.Vehicle;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
private VehicleRepository repository;
private VehicleMapper mapper;

public VehicleService(VehicleRepository repository, VehicleMapper mapper) {
	this.repository = repository;
    this.mapper = mapper;
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

}
