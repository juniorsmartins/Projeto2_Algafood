CREATE TABLE IF NOT EXISTS pedidos(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    subtotal DECIMAL(10,2),
    taxa_frete DECIMAL(10,2) DEFAULT 0.00,
    valor_total DECIMAL(10,2),
    data_hora_criacao TIMESTAMP,
    data_confirmacao TIMESTAMP,
    data_cancelamento TIMESTAMP,
    data_entrega TIMESTAMP,
    status_pedido VARCHAR(25) NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    restaurante_id bigint NOT NULL,
    usuario_id bigint NOT NULL,

    endereco_cep VARCHAR(15) NOT NULL,
    endereco_bairro VARCHAR(100) NOT NULL,
    endereco_logradouro VARCHAR(150) NOT NULL,
    endereco_numero VARCHAR(10) NOT NULL,
    endereco_complemento VARCHAR(250),
    endereco_cidade_id bigint NOT NULL,

    CONSTRAINT fk_forma_pagamento_id_pedido FOREIGN KEY(forma_pagamento_id) REFERENCES formas_pagamento(id),
    CONSTRAINT fk_restaurante_id_pedido FOREIGN KEY(restaurante_id) REFERENCES restaurantes(id),
    CONSTRAINT fk_usuario_id_pedido FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_address_cidade_id_pedido FOREIGN KEY(endereco_cidade_id) REFERENCES cidades(id)
);

