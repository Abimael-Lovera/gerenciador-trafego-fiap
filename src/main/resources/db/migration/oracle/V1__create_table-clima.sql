CREATE SEQUENCE t_gti_clima_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE TABLE t_gti_clima (
    id_clima       NUMBER NOT NULL PRIMARY KEY,
    ds_condicao    VARCHAR2(50) NOT NULL,
    nr_temperatura FLOAT NOT NULL,
    nr_umidade     FLOAT NOT NULL,
    dt_registro    DATE NOT NULL
);
