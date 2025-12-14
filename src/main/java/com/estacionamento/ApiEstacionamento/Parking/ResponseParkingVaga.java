package com.estacionamento.ApiEstacionamento.Parking;

public record ResponseParkingVaga(
        int capacityTotalCar,
        int capacityFreeCar,
        int capacityTotalMoto,
        int capacityFreeMoto
) {
}
