package com.estacionamento.ApiEstacionamento.Service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class codeService {
    public Long CodeParking(){
        Random random = new Random();
        long code = 100_000_000L + (long)(random.nextDouble() * 900_000_000L);
        return code;
    }
}
