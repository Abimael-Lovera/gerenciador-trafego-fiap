package com.fiap.acidente.dto;

import com.fiap.acidente.model.Acidente;
import com.fiap.acidente.model.Gravidade;
import com.fiap.semaforo.dto.SemaforoViewDTO;

import java.time.LocalDate;

public record AcidenteViewDTO(
        Integer idRegAcidente,
        String localAcidente,
        LocalDate dataAcidente,
        Gravidade gravidade,
        Integer nrFluxoImpactado,
        SemaforoViewDTO semaforo
) {
    public AcidenteViewDTO(Acidente acidente) {
        this(acidente.getId(), acidente.getLocalAcidente(), acidente.getDataAcidente(), acidente.getGravidade(), acidente.getNrFluxoImpactado(), new SemaforoViewDTO(acidente.getSemaforo()));
    }
}
