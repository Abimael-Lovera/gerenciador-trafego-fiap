package com.fiap.semaforo.model;

import com.fiap.clima.model.Clima;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity(name = "semaforo")
@Table(name = "t_gti_semaforo")
public class Semaforo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "semaforo_seq")
    @SequenceGenerator(name = "semaforo_seq", sequenceName = "t_gti_semaforo_seq", allocationSize = 1)
    private Integer idSemaforo;

    @Size(max = 150, message = "A localização não pode exceder 150 caracteres.")
    @Column(name = "ds_localizacao", nullable = false, length = 150)
    private String dsLocalizacao;

    @Size(max = 10)
    private String dsEstado;

    private LocalDate dtUltAtualizacao;

    @ManyToOne
    @JoinColumn(name = "t_gti_clima_id_clima")
    private Clima clima;

    public Integer getIdSemaforo() {
        return idSemaforo;
    }

    public void setIdSemaforo(Integer idSemaforo) {
        this.idSemaforo = idSemaforo;
    }

    public String getDsLocalizacao() {
        return dsLocalizacao;
    }

    public void setDsLocalizacao(String dsLocalizacao) {
        this.dsLocalizacao = dsLocalizacao;
    }

    public @Size(max = 10) String getDsEstado() {
        return dsEstado;
    }

    public void setDsEstado(@Size(max = 10) String dsEstado) {
        this.dsEstado = dsEstado;
    }

    public LocalDate getDtUltAtualizacao() {
        return dtUltAtualizacao;
    }

    public void setDtUltAtualizacao(LocalDate dtUltAtualizacao) {
        this.dtUltAtualizacao = dtUltAtualizacao;
    }

    public Clima getClima() {
        return clima;
    }

    public void setClima(Clima clima) {
        this.clima = clima;
    }
}
