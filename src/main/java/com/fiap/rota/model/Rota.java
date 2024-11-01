package com.fiap.rota.model;

import com.fiap.acidente.model.Acidente;
import jakarta.persistence.*;

@Entity(name = "rota")
@Table(name = "t_gti_rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rota_seq")
    @SequenceGenerator(name = "rota_seq", sequenceName = "t_gti_rota_seq", allocationSize = 1)
    @Column(name = "id_rota")
    private Integer id;

    @Column(name = "ds_descricao_rota", nullable = false, length = 200)
    private String descricaoRota;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_status", nullable = false)
    private RotaStatus status;

    @ManyToOne
    @JoinColumn(name = "t_reg_acidente_id_reg_acidente")
    private Acidente acidente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoRota() {
        return descricaoRota;
    }

    public void setDescricaoRota(String descricaoRota) {
        this.descricaoRota = descricaoRota;
    }

    public RotaStatus getStatus() {
        return status;
    }

    public void setStatus(RotaStatus status) {
        this.status = status;
    }

    public Acidente getAcidente() {
        return acidente;
    }

    public void setAcidente(Acidente acidente) {
        this.acidente = acidente;
    }
}
