package com.estacionamento.ApiEstacionamento.Vehicle;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@Validated
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleMapper vehicleMapper;


    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
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
