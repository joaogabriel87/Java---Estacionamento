package com.estacionamento.ApiEstacionamento.Vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestLinkVehicle(

        @NotBlank(message = "A placa é obrigatória")
        @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres")
        String plate,
        @NotBlank(message = "A marca é obrigatória")
        @Size(max = 50, message = "A marca deve ter no máximo 50 caracteres")
        String brand,
        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 30, message = "A cor deve ter no máximo 30 caracteres")
        String color,
        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 50, message = "O modelo deve ter no máximo 50 caracteres")
        String model,
        @NotNull(message = "O tipo é obrigatório")
        TypeEnum type,
        String email
) {
}
