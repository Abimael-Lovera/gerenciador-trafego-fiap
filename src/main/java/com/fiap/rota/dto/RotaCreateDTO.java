package com.fiap.rota.dto;

import com.fiap.rota.model.RotaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RotaCreateDTO(

        @NotBlank(message = "A descrição da rota é obrigatória!")
        @Size(max = 200, message = "A descrição da rota deve ter no máximo 200 caracteres!")
        String descricaoRota,

        @NotNull(message = "Status deve ser 'ABERTA' ou 'FECHADA'")
        RotaStatus status,

        @NotNull(message = "O id do acidente é obrigatório!")
        Integer acidenteId
) {
}
