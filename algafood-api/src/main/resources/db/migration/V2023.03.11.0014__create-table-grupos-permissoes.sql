CREATE TABLE IF NOT EXISTS grupos_permissoes(
    grupo_id bigint NOT NULL,
    permissao_id bigint NOT NULL,

    CONSTRAINT fk_grupo_id_grupo_permissao FOREIGN KEY(grupo_id) REFERENCES grupos(id),
    CONSTRAINT fk_permissao_id_grupo_permissao FOREIGN KEY(permissao_id) REFERENCES permissoes(id),
    CONSTRAINT pk_grupos_permissoes PRIMARY KEY(grupo_id, permissao_id)
);

