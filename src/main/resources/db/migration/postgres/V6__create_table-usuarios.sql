-- Sequência para a tabela T_GTI_USUARIO
CREATE SEQUENCE t_gti_usuario_seq
    START WITH 1 INCREMENT BY 1
    NO CYCLE;

-- Criação da tabela T_GTI_USUARIO
CREATE TABLE t_gti_usuario
(
    usuario_id INTEGER      NOT NULL DEFAULT nextval('t_gti_usuario_seq') PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    senha      VARCHAR(100) NOT NULL,
    role       VARCHAR(50)           DEFAULT 'USER',

    CONSTRAINT uniq_email_usuario UNIQUE (email) -- Constraint de unicidade para EMAIL
);