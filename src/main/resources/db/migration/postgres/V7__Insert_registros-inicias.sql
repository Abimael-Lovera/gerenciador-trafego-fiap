-- Inserções para t_gti_clima
INSERT INTO t_gti_clima (ds_condicao, nr_temperatura, nr_umidade, dt_registro)
VALUES ('Ensolarado', 28.5, 65.0, '2024-11-05'),
       ('Chuvoso', 22.3, 85.5, '2024-11-05'),
       ('Nublado', 25.0, 75.0, '2024-11-05'),
       ('Parcialmente Nublado', 26.8, 70.0, '2024-11-05'),
       ('Tempestade', 20.5, 90.0, '2024-11-05');

-- Inserções para t_gti_semaforo
INSERT INTO t_gti_semaforo (ds_localizacao, ds_estado, nr_duracao_estado, dt_ult_atualizacao, t_gti_clima_id_clima)
VALUES ('Av. Paulista com Rua Augusta', 'vermelho', 60, '2024-11-05', 1),
       ('Av. Brigadeiro Faria Lima com Av. Rebouças', 'verde', 45, '2024-11-05', 2),
       ('Rua Oscar Freire com Rua Augusta', 'amarelo', 30, '2024-11-05', 3),
       ('Av. 23 de Maio com Av. Paulista', 'vermelho', 60, '2024-11-05', 4),
       ('Av. Brasil com Av. Europa', 'verde', 45, '2024-11-05', 5);

-- Inserções para t_gti_reg_acidente
INSERT INTO t_gti_reg_acidente (ds_local_acidente, dt_acidente, ds_gravidade, nr_fluxo_impactado,
                                t_gti_semaforo_id_semaforo)
VALUES ('Av. Paulista, 1000', '2024-11-05', 'LEVE', 100, 1),
       ('Av. Brigadeiro Faria Lima, 2000', '2024-11-05', 'MODERADO', 250, 2),
       ('Rua Oscar Freire, 500', '2024-11-05', 'GRAVE', 500, 3),
       ('Av. 23 de Maio, 1500', '2024-11-05', 'MODERADO', 300, 4),
       ('Av. Brasil, 800', '2024-11-05', 'LEVE', 150, 5);

-- Inserções para t_gti_rota
INSERT INTO t_gti_rota (ds_descricao_rota, ds_status, t_reg_acidente_id_reg_acidente)
VALUES ('Rota alternativa Paulista-Centro', 'ABERTA', 1),
       ('Rota Faria Lima-Pinheiros', 'FECHADA', 2),
       ('Rota Jardins-Paraíso', 'ABERTA', 3),
       ('Rota Centro-Sul', 'ABERTA', 4),
       ('Rota Zona Norte-Centro', 'FECHADA', 5);

-- Inserções para t_gti_sensor_trafego
INSERT INTO t_gti_sensor_trafego (qt_fluxo_veiculos, nr_visibilidade, dt_deteccao, t_gti_semaforo_id_semaforo)
VALUES (500, 100, '2024-11-05', 1),
       (750, 90, '2024-11-05', 2),
       (300, 85, '2024-11-05', 3),
       (600, 95, '2024-11-05', 4),
       (400, 80, '2024-11-05', 5);