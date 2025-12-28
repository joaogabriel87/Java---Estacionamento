package com.estacionamento.ApiEstacionamento.Parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RequestCreateParking(

    @NotBlank
    String name,
    @NotNull
    @Positive
    int capacityTotalCar,
    @NotNull
    @Positive
    int capacityTotalMoto,
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
