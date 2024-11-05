CREATE SEQUENCE t_gti_clima_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE t_gti_clima
(
    id_clima       INTEGER     NOT NULL PRIMARY KEY DEFAULT nextval('t_gti_clima_seq'),
    ds_condicao    VARCHAR(50) NOT NULL,
    nr_temperatura FLOAT(5)    NOT NULL,
    nr_umidade     FLOAT(6)    NOT NULL,
    dt_registro    DATE        NOT NULL
)