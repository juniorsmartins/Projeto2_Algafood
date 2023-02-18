INSERT INTO estados(id, nome) VALUES(1, 'Rio Grande do Sul');
INSERT INTO estados(id, nome) VALUES(2, 'Santa Catarina');
INSERT INTO estados(id, nome) VALUES(3, 'Paraná');
INSERT INTO estados(id, nome) VALUES(4, 'São Paulo');
INSERT INTO estados(id, nome) VALUES(5, 'Mato Grosso');
INSERT INTO estados(id, nome) VALUES(6, 'Minas Gerais');

INSERT INTO cidades(id, nome, estado_id) VALUES(1, 'Porto Alegre', 1);
INSERT INTO cidades(id, nome, estado_id) VALUES(2, 'Dom Pedrito', 1);
INSERT INTO cidades(id, nome, estado_id) VALUES(3, 'São José', 2);
INSERT INTO cidades(id, nome, estado_id) VALUES(4, 'Toledo', 3);
INSERT INTO cidades(id, nome, estado_id) VALUES(5, 'Cuiabá', 5);
INSERT INTO cidades(id, nome, estado_id) VALUES(6, 'Iturama', 6);

INSERT INTO cozinhas(id, nome) VALUES(1, 'Brasileira');
INSERT INTO cozinhas(id, nome) VALUES(2, 'Japonesa');
INSERT INTO cozinhas(id, nome) VALUES(3, 'Italiana');
INSERT INTO cozinhas(id, nome) VALUES(4, 'Estadunidense');

INSERT INTO restaurantes(id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro,
    endereco_numero, endereco_bairro) VALUES(1, 'Zé Delivery', 10.25, 1, 1, '77.777-777',
    'Avenida X', '2211', 'Norte II');

INSERT INTO restaurantes(id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero,
    nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES(1, 'Norte II', '77.777-777', 'Entrada lateral esquerda',
    'Avenida X', '2211', 'Zé Delivery', 10.25, 1, 1);

INSERT INTO restaurantes(id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero,
    nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES
    (2, 'Vila Mariana', '78.000-888', 'Casa cor bege', 'Avenida Rui Barbosa', '550', 'Picanha na Chapa', 12.80, 1, 2);

INSERT INTO restaurantes(id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero,
    nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES
    (3, 'Centro', '22.222-222', 'Edifício Rosa, apt 505', 'Rua Nobel', '3200', 'Sushi e Salmão', 08.55, 2, 4);

INSERT INTO restaurantes(id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero,
    nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES
    (4, 'Vila Militar', '44.444-440', 'Sobrado azul', 'Rua Duque de Caxias', '1810', 'Papito Churrascos', 9.35, 1, 6);

INSERT INTO formas_pagamento(id, descricao) VALUES(1, 'Dinheiro');
INSERT INTO formas_pagamento(id, descricao) VALUES(2, 'Boleto');
INSERT INTO formas_pagamento(id, descricao) VALUES(3, 'Pix');
INSERT INTO formas_pagamento(id, descricao) VALUES(4, 'Cartão de Débido');
INSERT INTO formas_pagamento(id, descricao) VALUES(5, 'Cartão de Crédito');

INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(1, 1);
INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(1, 2);
INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(1, 3);
INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(2, 3);
INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(3, 2);
INSERT INTO restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) VALUES(3, 5);

INSERT INTO permissoes(id, nome, descricao) VALUES(1, 'Cadastrar', 'Permissão para cadastrar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(2, 'Consultar', 'Permissão para consultar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(3, 'Atualizar', 'Permissão para atualizar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(4, 'Deletar', 'Permissão para deletar recursos.');

