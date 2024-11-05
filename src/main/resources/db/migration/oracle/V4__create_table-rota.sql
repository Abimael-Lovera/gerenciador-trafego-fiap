-- Sequência para a tabela t_gti_rota
CREATE SEQUENCE t_gti_rota_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

-- Criação da tabela t_gti_rota
CREATE TABLE t_gti_rota
(
    id_rota                        INTEGER PRIMARY KEY,
    ds_descricao_rota              VARCHAR2(200) NOT NULL,
    ds_status                      VARCHAR2(10) DEFAULT 'ABERTA' NOT NULL CHECK (ds_status IN ('ABERTA', 'FECHADA')),
    t_reg_acidente_id_reg_acidente INTEGER,

    CONSTRAINT fk_t_gti_rota_acidente FOREIGN KEY (t_reg_acidente_id_reg_acidente)
        REFERENCES t_gti_reg_acidente (id_reg_acidente)
);

-- Para definir o valor padrão do id_rota como a sequência
ALTER TABLE t_gti_rota
MODIFY (id_rota DEFAULT t_gti_rota_seq.NEXTVAL);
