CREATE SEQUENCE t_gti_rota_seq START WITH 1 INCREMENT BY 1 CACHE 1;

CREATE TABLE t_gti_rota
(
    id_rota                        INTEGER PRIMARY KEY DEFAULT nextval('t_gti_rota_seq'),
    ds_descricao_rota              VARCHAR(200)                         NOT NULL,
    ds_status                      VARCHAR(10)         DEFAULT 'ABERTA' NOT NULL CHECK (ds_status IN ('ABERTA', 'FECHADA')),
    t_reg_acidente_id_reg_acidente INTEGER,
    CONSTRAINT fk_t_gti_rota_acidente FOREIGN KEY (t_reg_acidente_id_reg_acidente)
        REFERENCES t_gti_reg_acidente (id_reg_acidente)
);
