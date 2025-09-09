package com.estacionamento.ApiEstacionamento.Dto.ClientsDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientsDto(
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "Informe uma placa valida")
        String  document,
        @NotBlank
        String name,
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "Informe um numero correto")
        String phone

) {
}
