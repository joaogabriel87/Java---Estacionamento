package com.estacionamento.ApiEstacionamento.Erro;

public class ParkingisNull extends RuntimeException {
    public ParkingisNull() {
        super("Esse estacionamento não existe");
    }

    public ParkingisNull(String message) {
        super(message);
    }
}
