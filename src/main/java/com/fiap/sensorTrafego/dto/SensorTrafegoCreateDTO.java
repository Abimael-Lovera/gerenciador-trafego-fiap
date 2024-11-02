package com.fiap.sensorTrafego.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SensorTrafegoCreateDTO(
        @NotNull(message = "O número de fluxo de veículos é obrigatório!")
        @Positive(message = "O número de fluxo de veículos deve ser positivo!")
        Integer qtFluxoVeiculos,

        @NotNull(message = "A visibilidade é obrigatória!")
        @Positive(message = "A visibilidade deve ser positivo!")
        Integer nrVisibilidade,

        @NotNull(message = "A data de detecção é obrigatória!")
        LocalDate dtDeteccao,

        @NotNull(message = "O id do semaforo é obrigatório!")
        Integer semaforoId
) {
}
