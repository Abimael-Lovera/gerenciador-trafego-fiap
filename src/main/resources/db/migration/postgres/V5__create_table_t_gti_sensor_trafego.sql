CREATE SEQUENCE t_gti_sensor_trafego_seq START 1 INCREMENT 1 CACHE 1;

CREATE TABLE t_gti_sensor_trafego
(
    id_sensor                  INTEGER PRIMARY KEY DEFAULT nextval('t_gti_sensor_trafego_seq'),
    qt_fluxo_veiculos          INTEGER NOT NULL,
    nr_visibilidade            INTEGER NOT NULL,
    dt_deteccao                DATE    NOT NULL,
    t_gti_semaforo_id_semaforo INTEGER NOT NULL,
    CONSTRAINT fk_t_gti_sensor_trafego_semaforo FOREIGN KEY (t_gti_semaforo_id_semaforo)
        REFERENCES t_gti_semaforo (id_semaforo)
);
