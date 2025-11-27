package com.estacionamento.ApiEstacionamento.Vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VehicleDto(
        @NotBlank
        @Pattern(regexp = "\\d{7}", message = "Informe uma placa valida")
        String placa,
        @NotNull(message = "Tipo obrigatorio")
        TypeEnum tipo

) {
}
