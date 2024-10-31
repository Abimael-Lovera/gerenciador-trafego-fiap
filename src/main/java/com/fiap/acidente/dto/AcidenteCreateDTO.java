package com.fiap.acidente.dto;

import com.fiap.acidente.model.Gravidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AcidenteCreateDTO(
        @NotBlank(message = "O local do acidente é obrigatório!")
        String localAcidente,
        @NotNull(message = "A data do acidente é obrigatória!")
        LocalDate dataAcidente,
        @NotNull(message = "A gravidade do acidente é obrigatória!")
        Gravidade gravidade,
        @NotNull(message = "O número do fluxo impactado é obrigatório!")
        Integer nrFluxoImpactado,
        @NotNull(message = "A id do semaforo é obrigatória!")
        Integer semaforoId
) {
}
