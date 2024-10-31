package com.fiap.clima.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "clima")
@Table(name = "t_gti_clima")
public class Clima {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clima_seq")
    @SequenceGenerator(name = "clima_seq", sequenceName = "t_gti_clima_seq", allocationSize = 1)
    private Long idClima;
    @Column(
            name = "ds_condicao",
            length = 150
    )
    private String dsCondicao;

    @Column(name = "nr_temperatura", nullable = false)
    private Double nrTemperatura;

    @Column(name = "nr_umidade", nullable = false)
    private Double nrUmidade;

    @Column(name = "dt_registro", nullable = false)
    private LocalDate dtRegistro;

    public Long getIdClima() {
        return idClima;
    }

    public void setIdClima(Long idClima) {
        this.idClima = idClima;
    }

    public String getDsCondicao() {
        return dsCondicao;
    }

    public void setDsCondicao(String dsCondicao) {
        this.dsCondicao = dsCondicao;
    }

    public Double getNrTemperatura() {
        return nrTemperatura;
    }

    public void setNrTemperatura(Double nrTemperatura) {
        this.nrTemperatura = nrTemperatura;
    }

    public Double getNrUmidade() {
        return nrUmidade;
    }

    public void setNrUmidade(Double nrUmidade) {
        this.nrUmidade = nrUmidade;
    }


    public LocalDate getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(LocalDate dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    @Override
    public String toString() {
        return "Clima{" +
                "idClima=" + idClima +
                ", dsCondicao='" + dsCondicao + '\'' +
                ", nrTemperatura=" + nrTemperatura +
                ", nrUmidade=" + nrUmidade +
                ", dtRegistro=" + dtRegistro +
                '}';
    }
}
