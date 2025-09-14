package com.estacionamento.ApiEstacionamento.Controller.Parking;

import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ParkingDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ReportDto;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParking;
import com.estacionamento.ApiEstacionamento.Dto.ParkingDto.ResponseParkingExit;
import com.estacionamento.ApiEstacionamento.Dto.VehicleDto.VehicleDto;
import com.estacionamento.ApiEstacionamento.Entity.Parking.ParkingEntity;
import com.estacionamento.ApiEstacionamento.Mapper.ParkingMapper;
import com.estacionamento.ApiEstacionamento.Service.ParkingService.ParkingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@Validated
public class ParkingController {
    ParkingMapper  parkingMapper;
    ParkingService  parkingService;

    public ParkingController(ParkingMapper parkingMapper, ParkingService parkingService) {
        this.parkingMapper = parkingMapper;
        this.parkingService = parkingService;
    }

    @PostMapping
    public ResponseEntity<ResponseParking>enterParking(@Valid @RequestBody VehicleDto dto){
        ParkingEntity entity = parkingService.entrada(dto);
        ResponseParking response =  parkingMapper.toResponse(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<ResponseParking>> getParking(){
        List<ParkingEntity> parkings = parkingService.allParkings();
        List<ResponseParking> responseParkings = parkings.stream().map(parkingMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(responseParkings);
    }

    @GetMapping("/report")
    public ResponseEntity<ReportDto> report(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        ReportDto report =  parkingService.report(data);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @PatchMapping("/{code}/checkout")
    public ResponseEntity<ResponseParkingExit> exitParking(@PathVariable String code) {
        ParkingEntity entity = parkingService.saida(code);
        ResponseParkingExit response = parkingMapper.toResponseExit(entity); //
        return ResponseEntity.ok(response);
    }
}
