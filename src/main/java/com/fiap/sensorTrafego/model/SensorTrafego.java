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
    @Column(name = "id_sensor")
    private Integer id;

    @Column(name = "qt_fluxo_veiculos", nullable = false)
    private Integer qtFluxoVeiculos;


    @Column(name = "nr_visibilidade", nullable = false)
    private Integer nrVisibilidade;

    @Column(name = "dt_deteccao", nullable = false)
    private LocalDate dtDeteccao;

    @ManyToOne
    @JoinColumn(name = "t_gti_semaforo_id_semaforo", nullable = false)
    private Semaforo semaforo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtFluxoVeiculos() {
        return qtFluxoVeiculos;
    }

    public void setQtFluxoVeiculos(Integer fluxoVeiculos) {
        this.qtFluxoVeiculos = fluxoVeiculos;
    }

    public Integer getNrVisibilidade() {
        return nrVisibilidade;
    }

    public void setNrVisibilidade(Integer visibilidade) {
        this.nrVisibilidade = visibilidade;
    }

    public LocalDate getDtDeteccao() {
        return dtDeteccao;
    }

    public void setDtDeteccao(LocalDate deteccao) {
        this.dtDeteccao = deteccao;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    @Override
    public String toString() {
        return "SensorTrafego{" +
                "id=" + id +
                ", qtFluxoVeiculos=" + qtFluxoVeiculos +
                ", nrVisibilidade=" + nrVisibilidade +
                ", dtDeteccao=" + dtDeteccao +
                ", semaforo=" + semaforo +
                '}';
    }
}
