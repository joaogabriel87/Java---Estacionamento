package com.estacionamento.ApiEstacionamento.Service.ClientsService;

import com.estacionamento.ApiEstacionamento.Dto.ClientsDto.ClientsDto;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Clients.ClientsEntity;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ClientsMapper;
import com.estacionamento.ApiEstacionamento.Mapper.VehicleMapper;
import com.estacionamento.ApiEstacionamento.Repository.ClientsRepository;
import com.estacionamento.ApiEstacionamento.Repository.VehicleRepository;
import com.estacionamento.ApiEstacionamento.Service.VehicleService.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {
    ClientsRepository clientsRepository;
    ClientsMapper clientsMapper;
    VehicleService vehicleService;
    VehicleRepository vehicleRepository;
    VehicleMapper vehicleMapper;
    public ClientsService(ClientsRepository clientsRepository,  ClientsMapper clientsMapper,   VehicleService vehicleService,  VehicleRepository vehicleRepository,   VehicleMapper vehicleMapper) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
        this.vehicleService = vehicleService;
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public ClientsEntity newClient(ClientsDto dto) {
        ClientsEntity Entity = clientsMapper.toEntity(dto);
        ClientsEntity newEntity = clientsRepository.save(Entity);
        return clientsRepository.save(newEntity);

    }
    public VehicleEntity AddVehicle(VehicleDto vehicleDto, String docs) {
        System.out.println("Entrou aqui" + vehicleDto.toString());
        ClientsEntity client = clientsRepository.findByDocument(docs);
        if (client == null){
            throw new EntityNotFoundException("Cliente nao encontrado");
        }

        VehicleEntity vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicle.setClient(client);
        client.addVehicle(vehicle);

        return vehicleRepository.save(vehicle);

    }

    public List<ClientsEntity> getAllClients() {
        return clientsRepository.findAll();
    }
}
