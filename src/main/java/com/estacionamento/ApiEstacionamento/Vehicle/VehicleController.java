package com.estacionamento.ApiEstacionamento.Vehicle;

import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@Validated
public class VehicleController {
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private VehicleService vehicleService;
    private VehicleMapper vehicleMapper;


    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @PostMapping
    public ResponseEntity<VehicleEntity> create(@Valid @RequestBody VehicleDto dto){
        logger.info("Creating vehicleController init {}", dto);
        VehicleEntity entity = vehicleService.create(dto);
        logger.info("VehicleController final {}", entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<List<ResponseVehiclePlate>> getByPlate(@PathVariable("plate") String plate){
        logger.info("getByPlateController init {}", plate);
        List<ResponseVehiclePlate> response =  vehicleService.getVehiclePlate(plate);
        logger.info("VehicleController getByPlateController init {}", response);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/link/vehicle")
    public ResponseEntity<VehicleEntity> linkVehicle(@Valid @RequestBody RequestLinkVehicle dto)
            throws URISyntaxException, IOException, InterruptedException {
        logger.info("linkVehicleController init {}", dto);
        VehicleEntity vehicle = vehicleService.linkVehicle(dto);
        logger.info("VehicleController linkVehicleController final {}", vehicle);
        return ResponseEntity.ok(vehicle);
    }


}
