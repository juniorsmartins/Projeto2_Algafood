CREATE TABLE IF NOT EXISTS restaurantes(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    taxa_frete DECIMAL(10,2) DEFAULT 0.00 NOT NULL,
    data_hora_utc_cadastro TIMESTAMP NOT NULL,
    data_hora_utc_atualizacao TIMESTAMP,
    cozinha_id bigint NOT NULL,

    endereco_cep VARCHAR(15) NOT NULL,
    endereco_bairro VARCHAR(100) NOT NULL,
    endereco_logradouro VARCHAR(150) NOT NULL,
    endereco_numero VARCHAR(10) NOT NULL,
    endereco_complemento VARCHAR(250),
    endereco_cidade_id bigint NOT NULL,

    CONSTRAINT constraint_check_nome_restaurante CHECK(nome <> ''),
    CONSTRAINT fk_endereco_cidade_id_restaurante FOREIGN KEY(endereco_cidade_id) REFERENCES cidades(id),
    CONSTRAINT fk_cozinha_id_restaurante FOREIGN KEY(cozinha_id) REFERENCES cozinhas(id)
);

