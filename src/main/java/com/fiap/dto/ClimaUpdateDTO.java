package com.fiap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClimaUpdateDTO(
        @NotBlank(message = "O campo DS_CONDICAO é obrigatório!")
        @Size(min = 1, max = 150, message = "O campo DS_CONDICAO deve conter entre 1 e 50 caracteres")
        String dsCondicao,

        @NotNull(message = "O campo 'nrTemperatura' é obrigatório")
        Double nrTemperatura,

        @NotNull(message = "O campo 'nrUmidade' é obrigatório")
        Double nrUmidade,

        @NotNull(message = "O campo 'dtRegistro' é obrigatório")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDate dtRegistro
) {
}
