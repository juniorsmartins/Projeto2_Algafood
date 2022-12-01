INSERT INTO cozinhas(id, nome) VALUES(1, 'Brasileira');
INSERT INTO cozinhas(id, nome) VALUES(2, 'Japonesa');
INSERT INTO cozinhas(id, nome) VALUES(3, 'Italiana');
INSERT INTO cozinhas(id, nome) VALUES(4, 'Estadunidense');

INSERT INTO restaurantes(nome, taxa_frete, cozinha_id) VALUES('Zé Delivery', 10.25, 4);
INSERT INTO restaurantes(nome, taxa_frete, cozinha_id) VALUES('Picanha na Chapa', 12.80, 1);
INSERT INTO restaurantes(nome, taxa_frete, cozinha_id) VALUES('Sushi e Salmão', 8.55, 2);
INSERT INTO restaurantes(nome, taxa_frete, cozinha_id) VALUES('Papito Churrascos', 9.35, 1);

INSERT INTO formas_de_pagar(id, descricao) VALUES(1, 'Dinheiro');
INSERT INTO formas_de_pagar(id, descricao) VALUES(2, 'Boleto');
INSERT INTO formas_de_pagar(id, descricao) VALUES(3, 'Pix');
INSERT INTO formas_de_pagar(id, descricao) VALUES(4, 'Cartão de Débido');
INSERT INTO formas_de_pagar(id, descricao) VALUES(5, 'Cartão de Crédito');

INSERT INTO permissoes(id, nome, descricao) VALUES(1, 'Cadastrar', 'Permissão para cadastrar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(2, 'Consultar', 'Permissão para consultar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(3, 'Atualizar', 'Permissão para atualizar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(4, 'Deletar', 'Permissão para deletar recursos.');



