alter table estados disable trigger all;
alter table cidades disable trigger all;
alter table cozinhas disable trigger all;

delete from estados;
delete from cidades;
delete from cozinhas;

alter table estados enable trigger all;
alter table cidades enable trigger all;
alter table cozinhas enable trigger all;

alter sequence estados_id_seq restart with 1;
alter sequence cidades_id_seq restart with 1;
alter sequence cozinhas_id_seq restart with 1;


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


