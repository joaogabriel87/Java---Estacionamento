package com.estacionamento.ApiEstacionamento.Erro;

public class ParkingIsOccupiedTrue extends RuntimeException {
    public ParkingIsOccupiedTrue() {
        super("O estacionamento não esta sendo ocupado");
    }

    public ParkingIsOccupiedTrue(String message) {
        super(message);
    }
}
