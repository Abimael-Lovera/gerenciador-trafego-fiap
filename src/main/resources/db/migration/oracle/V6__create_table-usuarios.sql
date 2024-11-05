-- Sequência para a tabela T_GTI_USUARIO
CREATE SEQUENCE t_gti_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE;

-- Criação da tabela T_GTI_USUARIO
CREATE TABLE t_gti_usuario (
    usuario_id NUMBER NOT NULL PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) NOT NULL,
    senha VARCHAR2(100) NOT NULL,
    role VARCHAR2(50) DEFAULT 'USER',  -- Campo ROLE com valor padrão

    CONSTRAINT uniq_email_usuario UNIQUE (email)  -- Constraint de unicidade para EMAIL
);

-- Para definir o valor padrão do usuario_id como a sequência
ALTER TABLE t_gti_usuario
MODIFY (usuario_id DEFAULT t_gti_usuario_seq.NEXTVAL);
