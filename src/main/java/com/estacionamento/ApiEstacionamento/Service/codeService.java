package com.estacionamento.ApiEstacionamento.Service;

import com.estacionamento.ApiEstacionamento.Parking.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class codeService {
    private static final Logger logger = LoggerFactory.getLogger(codeService.class);

    public Long CodeParking(){
        logger.info("Iniciando Parking Service");
        Random random = new Random();
        long code = 100_000_000L + (long)(random.nextDouble() * 900_000_000L);
        logger.info("Parking code : " + code);
        return code;
    }
}
