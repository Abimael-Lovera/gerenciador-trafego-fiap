package com.fiap.acidente.model;

import com.fiap.semaforo.model.Semaforo;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "registro_acidente")
@Table(name = "t_gti_reg_acidente")
public class Acidente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_acidente_seq")
    @SequenceGenerator(name = "reg_acidente_seq", sequenceName = "t_gti_reg_acidente_seq", allocationSize = 1)
    @Column(name = "id_reg_acidente", nullable = false)
    private Integer id;

    @Column(name = "ds_local_acidente", nullable = false, length = 150)
    private String localAcidente;

    @Column(name = "dt_acidente", nullable = false)
    private LocalDate dataAcidente;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_gravidade", nullable = false)
    private Gravidade gravidade;

    @Column(name = "nr_fluxo_impactado", nullable = false)
    private Integer nrFluxoImpactado;

    @ManyToOne
    @JoinColumn(name = "t_gti_semaforo_id_semaforo")
    private Semaforo semaforo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idAcidente) {
        this.id = idAcidente;
    }

    public String getLocalAcidente() {
        return localAcidente;
    }

    public void setLocalAcidente(String localAcidente) {
        this.localAcidente = localAcidente;
    }

    public LocalDate getDataAcidente() {
        return dataAcidente;
    }

    public void setDataAcidente(LocalDate dataAcidente) {
        this.dataAcidente = dataAcidente;
    }

    public Gravidade getGravidade() {
        return gravidade;
    }

    public void setGravidade(Gravidade gravidade) {
        this.gravidade = gravidade;
    }

    public Integer getNrFluxoImpactado() {
        return nrFluxoImpactado;
    }

    public void setNrFluxoImpactado(Integer fluxoImpactado) {
        this.nrFluxoImpactado = fluxoImpactado;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    @Override
    public String toString() {
        return "Acidente{" +
                "id=" + id +
                ", localAcidente='" + localAcidente + '\'' +
                ", dataAcidente=" + dataAcidente +
                ", gravidade=" + gravidade +
                ", nrFluxoImpactado=" + nrFluxoImpactado +
                ", semaforo=" + semaforo +
                '}';
    }


}
