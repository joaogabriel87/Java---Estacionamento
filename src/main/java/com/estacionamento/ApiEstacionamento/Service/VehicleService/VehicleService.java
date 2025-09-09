package com.estacionamento.ApiEstacionamento.Service.VehicleService;

import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ClientsMapper;
import com.estacionamento.ApiEstacionamento.Mapper.VehicleMapper;
import com.estacionamento.ApiEstacionamento.Repository.VehicleRepository;
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
    System.out.println("Entrou aqui" + dto.toString());
        VehicleEntity entity = mapper.toEntity(dto);
        VehicleEntity newEntity = repository.save(entity);
        return newEntity;
 }

 public List<VehicleEntity> all() {
    return repository.findAll();
 }

}
