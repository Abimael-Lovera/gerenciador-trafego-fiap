CREATE SEQUENCE t_gti_semaforo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE t_gti_semaforo
(
    id_semaforo      INTEGER      NOT NULL PRIMARY KEY DEFAULT nextval('t_gti_semaforo_seq'),
    ds_localizacao   VARCHAR(150) NOT NULL,
    ds_estado        VARCHAR(10)  DEFAULT 'vermelho' NOT NULL,
    dt_ult_atualizacao DATE       NOT NULL,
    t_gti_clima_id_clima INTEGER,

    CONSTRAINT ck_gti_semaforo_estado CHECK (ds_estado IN ('vermelho', 'amarelo', 'verde')),
    CONSTRAINT fk_gti_semaforo_clima FOREIGN KEY (t_gti_clima_id_clima) REFERENCES t_gti_clima(id_clima)
);