package com.estacionamento.ApiEstacionamento.Parking;

import com.estacionamento.ApiEstacionamento.ApiEstacionamentoApplication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/parking")
@Validated
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingMapper parkingMapper;
    private final ParkingService parkingService;
    private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @PostMapping
    public ResponseEntity<ResponseParking>createParking(@Valid @RequestBody RequestCreateParking dto){
        logger.info("Parking created init");
        ParkingEntity parkingEntity = parkingService.createParking(dto);
        logger.info("Parking {} Created", parkingEntity);
        ResponseParking response =  parkingMapper.toResponseParking(parkingEntity);
        logger.info("ParkingEntity convert ResponseParking {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("report/{local}/{date}")
    public ResponseEntity<ReportDto>getReport(@PathVariable String local ,@PathVariable LocalDate date){
        logger.info("Parking report init");
        ReportDto report = parkingService.report(local,date);
        logger.info("report final: {}", report);
        return  ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @GetMapping("vagas/{local}")
    public ResponseEntity<ResponseParkingVaga> getVagas(@PathVariable String local){
        logger.info("Parking vagas init");
        ResponseParkingVaga response =  parkingService.getVaga(local);
        logger.info("getVaga final: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
