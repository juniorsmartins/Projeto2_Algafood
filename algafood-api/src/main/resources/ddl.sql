
    create table cidades (
       id  bigserial not null,
        nome varchar(80) not null,
        estado_id int8 not null,
        primary key (id)
    );

    create table cozinhas (
       id  bigserial not null,
        nome varchar(80) not null,
        primary key (id)
    );

    create table estados (
       id  bigserial not null,
        nome varchar(80) not null,
        primary key (id)
    );

    create table formas_pagamento (
       id  bigserial not null,
        descricao varchar(250) not null,
        primary key (id)
    );

    create table grupos (
       id  bigserial not null,
        nome varchar(255) not null,
        primary key (id)
    );

    create table grupos_permissoes (
       grupo_id int8 not null,
        permissao_id int8 not null
    );

    create table permissoes (
       id  bigserial not null,
        descricao varchar(250) not null,
        nome varchar(80) not null,
        primary key (id)
    );

    create table pratos (
       id  bigserial not null,
        descricao varchar(250),
        nome varchar(100) not null,
        nome_autor varchar(150),
        peso float4 not null,
        valor numeric(19, 2) not null,
        primary key (id)
    );

    create table produtos (
       id  bigserial not null,
        ativo boolean,
        descricao varchar(255),
        nome varchar(255),
        preco numeric(2, 0),
        restaurante_id int8 not null,
        primary key (id)
    );

    create table restaurantes (
       id  bigserial not null,
        data_atualizacao timestamp,
        data_cadastro timestamp not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(80) not null,
        taxa_frete numeric(19, 2) not null,
        cozinha_id int8 not null,
        endereco_cidade_id int8,
        primary key (id)
    );

    create table restaurantes_formas_pagamento (
       restaurante_id int8 not null,
        forma_pagamento_id int8 not null
    );

    create table usuarios (
       id  bigserial not null,
        data_cadastro timestamp not null,
        email varchar(255) not null,
        nome varchar(255) not null,
        senha varchar(255) not null,
        primary key (id)
    );

    create table usuarios_grupos (
       usuario_id int8 not null,
        grupo_id int8 not null
    );

    alter table if exists cidades 
       add constraint FKdt0b3ronwpi1upsrhaeq6r69n 
       foreign key (estado_id) 
       references estados;

    alter table if exists grupos_permissoes 
       add constraint FKfqmdfklkug3iagcjvpmytb1fn 
       foreign key (permissao_id) 
       references permissoes;

    alter table if exists grupos_permissoes 
       add constraint FKbon4g13b7en00ty174t12ijc7 
       foreign key (grupo_id) 
       references grupos;

    alter table if exists produtos 
       add constraint FKsl3jhd8nhd103c5nm6yocnnkx 
       foreign key (restaurante_id) 
       references restaurantes;

    alter table if exists restaurantes 
       add constraint FKgdtjhnl3atr87y5s0tcnr2i5q 
       foreign key (cozinha_id) 
       references cozinhas;

    alter table if exists restaurantes 
       add constraint FKjxh9a5cs0ke85v1t0uh6p9shb 
       foreign key (endereco_cidade_id) 
       references cidades;

    alter table if exists restaurantes_formas_pagamento 
       add constraint FKm917uvu83vf1dr9f1gex7opjs 
       foreign key (forma_pagamento_id) 
       references formas_pagamento;

    alter table if exists restaurantes_formas_pagamento 
       add constraint FKdcf58nl2v0qpuhbtk4hvdeleu 
       foreign key (restaurante_id) 
       references restaurantes;

    alter table if exists usuarios_grupos 
       add constraint FKdh3qddmbja2u6ioqbpdscom1t 
       foreign key (grupo_id) 
       references grupos;

    alter table if exists usuarios_grupos 
       add constraint FKm3qhj4o3gwf1l0qub0kddvldd 
       foreign key (usuario_id) 
       references usuarios;
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
INSERT INTO formas_pagamento(id, descricao) VALUES(1, 'Dinheiro');
INSERT INTO formas_pagamento(id, descricao) VALUES(2, 'Boleto');
INSERT INTO formas_pagamento(id, descricao) VALUES(3, 'Pix');
INSERT INTO formas_pagamento(id, descricao) VALUES(4, 'Cartão de Débido');
INSERT INTO formas_pagamento(id, descricao) VALUES(5, 'Cartão de Crédito');
INSERT INTO permissoes(id, nome, descricao) VALUES(1, 'Cadastrar', 'Permissão para cadastrar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(2, 'Consultar', 'Permissão para consultar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(3, 'Atualizar', 'Permissão para atualizar recursos.');
INSERT INTO permissoes(id, nome, descricao) VALUES(4, 'Deletar', 'Permissão para deletar recursos.');
