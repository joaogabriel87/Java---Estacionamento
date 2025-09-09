package com.estacionamento.ApiEstacionamento.Controller.ClientsController;

import com.estacionamento.ApiEstacionamento.Dto.ClientsDto.ClientsDto;
import com.estacionamento.ApiEstacionamento.Dto.ClientsDto.ResponseClient;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.ResponseVehicle;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Clients.ClientsEntity;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ClientsMapper;
import com.estacionamento.ApiEstacionamento.Mapper.VehicleMapper;
import com.estacionamento.ApiEstacionamento.Repository.ClientsRepository;
import com.estacionamento.ApiEstacionamento.Service.ClientsService.ClientsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
@Validated
public class ClientsController {
    ClientsMapper clientsMapper;
    ClientsService clientsService;
    VehicleMapper vehicleMapper;

    public ClientsController(ClientsMapper clientsMapper, ClientsService clientsService, VehicleMapper vehicleMapper) {
        this.clientsMapper = clientsMapper;
        this.clientsService = clientsService;
        this.vehicleMapper = vehicleMapper;

    }

    @PostMapping
    public ResponseEntity<ResponseClient> newClient(@Valid @RequestBody ClientsDto dto){
        ClientsEntity saveClient = clientsService.newClient(dto);
        ResponseClient response = clientsMapper.toResponse(saveClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientsEntity>> getAllClients(){
        List<ClientsEntity> client =  clientsService.getAllClients();
        return ResponseEntity.status(200).body(client);
    }

    @PatchMapping("/{docs}/vehicle")
    public ResponseEntity<ResponseVehicle> addVehicle(@Valid @RequestBody VehicleDto dto, @PathVariable String docs){
        VehicleEntity vehicle = clientsService.AddVehicle(dto, docs);
        ResponseVehicle response =  vehicleMapper.toResponse(vehicle);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
