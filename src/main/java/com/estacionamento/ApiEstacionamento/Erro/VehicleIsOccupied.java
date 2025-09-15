package com.estacionamento.ApiEstacionamento.Erro;

public class VehicleIsOccupied extends RuntimeException {
    public VehicleIsOccupied(){
        super("O veiculo já esta com estacionamento ativo");
    }

    public VehicleIsOccupied(String message) {
        super(message);
    }
}
