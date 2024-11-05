CREATE SEQUENCE t_gti_semaforo_seq
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE;

CREATE TABLE t_gti_semaforo
(
    id_semaforo      INTEGER      NOT NULL PRIMARY KEY,
    ds_localizacao   VARCHAR2(150) NOT NULL,
    ds_estado        VARCHAR2(10)  DEFAULT 'vermelho' NOT NULL,
    nr_duracao_estado  INTEGER      NOT NULL,
    dt_ult_atualizacao DATE       NOT NULL,
    t_gti_clima_id_clima INTEGER,

    CONSTRAINT ck_gti_semaforo_estado CHECK (ds_estado IN ('vermelho', 'amarelo', 'verde')),
    CONSTRAINT fk_gti_semaforo_clima FOREIGN KEY (t_gti_clima_id_clima) REFERENCES t_gti_clima(id_clima)
);

-- Para definir o valor padrão do id_semaforo como a sequência
ALTER TABLE t_gti_semaforo
MODIFY (id_semaforo DEFAULT t_gti_semaforo_seq.NEXTVAL);
