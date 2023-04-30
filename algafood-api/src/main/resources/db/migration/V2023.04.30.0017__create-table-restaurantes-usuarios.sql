CREATE TABLE IF NOT EXISTS restaurantes_usuarios(
    restaurante_id bigint NOT NULL,
    usuario_id bigint NOT NULL,

    CONSTRAINT fk_restaurantes_usuarios_restaurante_id FOREIGN KEY(restaurante_id) REFERENCES restaurantes(id),
    CONSTRAINT fk_restaurantes_usuarios_usuario_id FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT pk_restaurantes_usuarios PRIMARY KEY(restaurante_id, usuario_id)
);

