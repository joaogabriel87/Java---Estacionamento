package com.estacionamento.ApiEstacionamento.Service.ParkingService;

import com.estacionamento.ApiEstacionamento.Entity.Vehicle.TypeEnum;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class codeService {
    public String CodeParking(TypeEnum type){
        String code = (type == TypeEnum.CAR ? "C" : "M") + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return code;
    }
}
