package com.fiap.sensorTrafego.model;

import com.fiap.semaforo.model.Semaforo;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_gti_sensor_trafego")
public class SensorTrafego {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_trafego_seq")
    @SequenceGenerator(name = "sensor_trafego_seq", sequenceName = "t_gti_sensor_trafego_seq", allocationSize = 1)
    private Integer id;


    @Column(name = "qt_fluxo_veiculos", nullable = false)
    private Integer fluxoVeiculos;


    @Column(name = "nr_visibilidade", nullable = false)
    private Integer visibilidade;

    @Column(name = "dt_deteccao", nullable = false)
    private LocalDate deteccao;

    @ManyToOne
    @JoinColumn(name = "t_gti_semaforo_id_semaforo", nullable = false)
    private Semaforo semaforo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFluxoVeiculos() {
        return fluxoVeiculos;
    }

    public void setFluxoVeiculos(Integer fluxoVeiculos) {
        this.fluxoVeiculos = fluxoVeiculos;
    }

    public Integer getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(Integer visibilidade) {
        this.visibilidade = visibilidade;
    }

    public LocalDate getDeteccao() {
        return deteccao;
    }

    public void setDeteccao(LocalDate deteccao) {
        this.deteccao = deteccao;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }
}
