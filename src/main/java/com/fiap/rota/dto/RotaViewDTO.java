package com.fiap.rota.dto;

import com.fiap.acidente.dto.AcidenteViewDTO;
import com.fiap.rota.model.Rota;
import com.fiap.rota.model.RotaStatus;

public record RotaViewDTO(
        Integer id,
        String descricaoRota,
        RotaStatus status,
        AcidenteViewDTO acidente
) {
    public RotaViewDTO(Rota rota) {
        this(rota.getId(), rota.getDescricaoRota(), rota.getStatus(), new AcidenteViewDTO(rota.getAcidente()));
    }
}
