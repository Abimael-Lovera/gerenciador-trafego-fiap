package com.fiap.semaforo.dto;

import com.fiap.clima.dto.ClimaViewDTO;
import com.fiap.semaforo.model.Semaforo;

import java.time.LocalDate;

public record SemaforoViewDTO(
        Integer idSemaforo,
        String dsLocalizacao,
        String dsEstado,
        LocalDate dtUltAtualizacao,
        ClimaViewDTO clima
) {
    public SemaforoViewDTO(Semaforo semaforo) {
        this(semaforo.getIdSemaforo(), semaforo.getDsLocalizacao(), semaforo.getDsEstado(), semaforo.getDtUltAtualizacao(), new ClimaViewDTO(semaforo.getClima()));
    }
}
