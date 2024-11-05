-- Sequência para a tabela t_gti_reg_acidente
CREATE SEQUENCE t_gti_reg_acidente_seq
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE;

-- Criação da tabela t_gti_reg_acidente com a constraint de gravidade
CREATE TABLE t_gti_reg_acidente
(
    id_reg_acidente            INTEGER        NOT NULL PRIMARY KEY,
    ds_local_acidente          VARCHAR2(150)   NOT NULL,
    dt_acidente                DATE           NOT NULL,
    ds_gravidade               VARCHAR2(10)    NOT NULL,
    nr_fluxo_impactado         INTEGER        NOT NULL,
    t_gti_semaforo_id_semaforo INTEGER,

    CONSTRAINT fk_t_gti_reg_acidente_semaforo FOREIGN KEY (t_gti_semaforo_id_semaforo)
        REFERENCES t_gti_semaforo (id_semaforo),
    CONSTRAINT chk_gravidade CHECK (ds_gravidade IN ('LEVE', 'MODERADO', 'GRAVE'))
);

-- Para definir o valor padrão do id_reg_acidente como a sequência
ALTER TABLE t_gti_reg_acidente
MODIFY (id_reg_acidente DEFAULT t_gti_reg_acidente_seq.NEXTVAL);
