package com.fiap.sensorTrafego.dto;

import com.fiap.semaforo.dto.SemaforoViewDTO;
import com.fiap.sensorTrafego.model.SensorTrafego;

import java.time.LocalDate;

public record SensorTrafegoViewDTO(
        Integer id,
        Integer qtFluxoVeiculos,
        Integer nrVisibilidade,
        LocalDate dtDeteccao,
        SemaforoViewDTO semaforo
) {
    public SensorTrafegoViewDTO(SensorTrafego sensorTrafego) {
        this(
                sensorTrafego.getId(),
                sensorTrafego.getQtFluxoVeiculos(),
                sensorTrafego.getNrVisibilidade(),
                sensorTrafego.getDtDeteccao(),
                new SemaforoViewDTO(sensorTrafego.getSemaforo())
        );
    }
}
