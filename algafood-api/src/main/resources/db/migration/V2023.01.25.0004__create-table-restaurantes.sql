CREATE TABLE IF NOT EXISTS restaurantes(
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    nome VARCHAR(80) NOT NULL,
    taxa_frete DECIMAL(10,2) NOT NULL,
    endereco_cep VARCHAR(15) NOT NULL,
    endereco_bairro VARCHAR(100) NOT NULL,
    endereco_logradouro VARCHAR NOT NULL,
    endereco_numero INTEGER NOT NULL,
    endereco_complemento VARCHAR(250),

    endereco_cidade_id INTEGER NOT NULL,
    cozinha_id bigint NOT NULL,

    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP,

    CONSTRAINT constraint_check_nome_restaurante CHECK(nome <> ''),
    CONSTRAINT fk_endereco_cidade_id FOREIGN KEY(endereco_cidade_id) REFERENCES cidades(id),
    CONSTRAINT fk_cozinha_id FOREIGN KEY(cozinha_id) REFERENCES cozinhas(id)
);

