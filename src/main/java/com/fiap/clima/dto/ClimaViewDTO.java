package com.fiap.clima.dto;


import com.fiap.clima.model.Clima;

import java.time.LocalDate;

public record ClimaViewDTO(
        Long idClima,
        String dsCondicao,
        Double nrTemperatura,
        Double nrUmidade,
        LocalDate dtRegistro
) {
    public ClimaViewDTO(Clima clima) {
        this(clima.getIdClima(), clima.getDsCondicao(), clima.getNrTemperatura(), clima.getNrUmidade(), clima.getDtRegistro());
    }
}
