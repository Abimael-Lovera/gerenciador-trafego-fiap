package com.fiap.sensorTrafego.dto;

import com.fiap.semaforo.dto.SemaforoViewDTO;
import com.fiap.sensorTrafego.model.SensorTrafego;

import java.time.LocalDate;

public record SensorTrafegoViewDTO(
        Integer id,
        Integer fluxoVeiculos,
        Integer visibilidade,
        LocalDate deteccao,
        SemaforoViewDTO semaforo
) {
    public SensorTrafegoViewDTO(SensorTrafego sensorTrafego) {
        this(
                sensorTrafego.getId(),
                sensorTrafego.getFluxoVeiculos(),
                sensorTrafego.getVisibilidade(),
                sensorTrafego.getDeteccao(),
                new SemaforoViewDTO(sensorTrafego.getSemaforo())
        );
    }
}
