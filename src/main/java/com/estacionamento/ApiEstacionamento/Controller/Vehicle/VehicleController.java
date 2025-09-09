package com.estacionamento.ApiEstacionamento.Controller.Vehicle;

import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.ResponseVehicle;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Vehicle.VehicleEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ClientsMapper;
import com.estacionamento.ApiEstacionamento.Mapper.VehicleMapper;
import com.estacionamento.ApiEstacionamento.Service.VehicleService.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@Validated
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleMapper vehicleMapper;
    private ClientsMapper clientsMapper;

    public VehicleController(VehicleService vehicleService, ClientsMapper clientsMapper,  VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.clientsMapper = clientsMapper;
        this.vehicleMapper = vehicleMapper;
    }

    @PostMapping
    public ResponseEntity<VehicleEntity> create(@Valid @RequestBody VehicleDto dto){
        VehicleEntity entity = vehicleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

//    @GetMapping
//    public ResponseEntity<List<ResponseVehicle>> getAll(){
//        List<VehicleEntity> vehicles = vehicleService.all();
//        List<ResponseVehicle> responseVehicles = vehicles.stream().map(vehicleMapper::toResponse).toList();
//        return ResponseEntity.status(HttpStatus.OK).body(responseVehicles);
//    }


}
