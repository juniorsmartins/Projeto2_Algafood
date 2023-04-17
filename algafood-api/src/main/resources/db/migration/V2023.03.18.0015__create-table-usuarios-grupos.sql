CREATE TABLE IF NOT EXISTS usuarios_grupos(
    usuario_id bigint NOT NULL,
    grupo_id bigint NOT NULL,

    CONSTRAINT fk_usuario_id_usuarios_grupos FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_grupo_id_usuarios_grupos FOREIGN KEY(grupo_id) REFERENCES grupos(id),
    CONSTRAINT pk_usuarios_grupos PRIMARY KEY(usuario_id, grupo_id)
);

