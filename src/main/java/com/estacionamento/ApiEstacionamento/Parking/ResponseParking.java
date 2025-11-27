package com.estacionamento.ApiEstacionamento.Parking;

public record ResponseParking(
    String nome,
    int quantidade_carro,
    int quantidade_moto
) {
}
