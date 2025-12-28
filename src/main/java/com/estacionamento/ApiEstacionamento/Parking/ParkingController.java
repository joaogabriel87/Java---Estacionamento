package com.estacionamento.ApiEstacionamento.Parking;

import com.estacionamento.ApiEstacionamento.ApiEstacionamentoApplication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/parking")
@Validated
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingMapper parkingMapper;
    private final ParkingService parkingService;

    @PostMapping
    public ResponseEntity<ResponseParking>createParking(@Valid @RequestBody RequestCreateParking dto){
        ParkingEntity parkingEntity = parkingService.createParking(dto);
        ResponseParking response =  parkingMapper.toResponseParking(parkingEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("report/{local}/{date}")
    public ResponseEntity<ReportDto>getReport(@PathVariable String local ,@PathVariable LocalDate date){
        ReportDto report = parkingService.report(local,date);
        return  ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @GetMapping("vagas/{local}")
    public ResponseEntity<ResponseParkingVaga> getVagas(@PathVariable String local){
        ResponseParkingVaga response =  parkingService.getVaga(local);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
