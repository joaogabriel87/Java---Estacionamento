package com.estacionamento.ApiEstacionamento.Vehicle;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{plate}")
    public ResponseEntity<List<ResponseVehiclePlate>> getByPlate(@PathVariable("plate") String plate){
        List<ResponseVehiclePlate> response =  vehicleService.getVehiclePlate(plate);
        return ResponseEntity.ok().body(response);
    }


}
