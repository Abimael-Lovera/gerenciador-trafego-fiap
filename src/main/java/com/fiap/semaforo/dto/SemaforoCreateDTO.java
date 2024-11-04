package com.fiap.semaforo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SemaforoCreateDTO(
        @NotBlank(message = "A localização é obrigatória.")
        String dsLocalizacao,

        @NotBlank(message = "O estado é obrigatório.")
        String dsEstado,

        @NotNull(message = "Número de duração é obrigatório")
        Integer nrDuracaoEstado,

        @NotNull(message = "A data de atualização é obrigatória.")
        LocalDate dtUltAtualizacao,

        @NotNull(message = "O id do clima é obrigatório.")
        Long climaId
) {
}
