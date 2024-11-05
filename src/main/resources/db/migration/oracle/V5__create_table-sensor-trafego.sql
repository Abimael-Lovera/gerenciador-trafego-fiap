-- Sequência para a tabela t_gti_sensor_trafego
CREATE SEQUENCE t_gti_sensor_trafego_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

-- Criação da tabela t_gti_sensor_trafego
CREATE TABLE t_gti_sensor_trafego
(
    id_sensor                  INTEGER PRIMARY KEY,
    qt_fluxo_veiculos          INTEGER NOT NULL,
    nr_visibilidade            INTEGER NOT NULL,
    dt_deteccao                DATE NOT NULL,
    t_gti_semaforo_id_semaforo INTEGER NOT NULL,

    CONSTRAINT fk_t_gti_sensor_trafego_semaforo FOREIGN KEY (t_gti_semaforo_id_semaforo)
        REFERENCES t_gti_semaforo (id_semaforo)
);

-- Para definir o valor padrão do id_sensor como a sequência
ALTER TABLE t_gti_sensor_trafego
MODIFY (id_sensor DEFAULT t_gti_sensor_trafego_seq.NEXTVAL);
