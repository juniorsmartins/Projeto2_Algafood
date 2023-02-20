alter table estados disable trigger all;
alter table cidades disable trigger all;
alter table cozinhas disable trigger all;
alter table restaurantes disable trigger all;

delete from estados;
delete from cidades;
delete from cozinhas;
delete from restaurantes;

alter table estados enable trigger all;
alter table cidades enable trigger all;
alter table cozinhas enable trigger all;
alter table restaurantes enable trigger all;

alter sequence estados_id_seq restart with 10;
alter sequence cidades_id_seq restart with 10;
alter sequence cozinhas_id_seq restart with 10;
alter sequence restaurantes_id_seq restart with 10;

INSERT INTO estados(id, nome) VALUES
    (1, 'Rio Grande do Sul'),
    (2, 'Santa Catarina'),
    (3, 'Paraná'),
    (4, 'São Paulo'),
    (5, 'Mato Grosso'),
    (6, 'Minas Gerais')
    On CONFLICT(id) DO NOTHING;

INSERT INTO cidades(id, nome, estado_id) VALUES
    (1, 'Porto Alegre', 1),
    (2, 'Dom Pedrito', 1),
    (3, 'São José', 2),
    (4, 'Toledo', 3),
    (5, 'Cuiabá', 5),
    (6, 'Iturama', 6)
    On CONFLICT(id) DO NOTHING;

INSERT INTO cozinhas(id, nome) VALUES
    (1, 'Brasileira'),
    (2, 'Japonesa'),
    (3, 'Italiana'),
    (4, 'Estadunidense')
    On CONFLICT(id) DO NOTHING;

INSERT INTO restaurantes(id, nome, taxa_frete, data_hora_utc_cadastro, cozinha_id, endereco_cep, endereco_bairro,
    endereco_logradouro, endereco_numero, endereco_cidade_id) VALUES
    (1, 'Casa do Porco', 30.10, now(), 1, '78000001', 'Centro-Sul', 'Avenida X', 1000, 1),
    (2, 'Narisawa', 42.90, now(), 2, '78000002', 'Zona Norte', 'Rua Central', 2500, 2),
    (3, 'White Rabbit', 80, now(), 4, '78000003', 'Alameda Vermelha', 'Avenida R', 4750, 6)
    On CONFLICT(id) DO NOTHING;

INSERT INTO formas_pagamento(id, descricao) VALUES
    (1, 'DINHEIRO'),
    (2, 'PIX'),
    (3, 'DEBITO'),
    (4, 'BOLETO'),
    (5, 'CREDITO')
    On CONFLICT DO NOTHING;

INSERT INTO restaurantes_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5)
    On CONFLICT DO NOTHING;

