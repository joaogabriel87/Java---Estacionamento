package com.estacionamento.ApiEstacionamento.Parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ParkingDto(

    @NotBlank
    String name,
    @NotNull
    @Positive
    int capacityCar,
    @NotNull
    @Positive
    int capacityMoto,
    @NotNull
    @Positive
    BigDecimal taxa_carro,
    @NotNull
    @Positive
    BigDecimal taxa_moto,
    @NotNull
    @Positive
    BigDecimal taxa_carro_ad,
    @NotNull
    @Positive
    BigDecimal taxa_moto_ad

) {
}
