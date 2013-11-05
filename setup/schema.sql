drop table if exists parametro;
drop table if exists operacao;
drop table if exists atributo;
drop table if exists tipo;
drop table if exists relacao_classe;
drop table if exists classe;
drop table if exists diagrama;

create table diagrama (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) not null
) ENGINE=INNODB;

create table classe (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) not null,
    parent_classe_id bigint,
    position_x float not null default 100,
    position_y float not null default 100,
    text_order int not null,
    diagrama_id bigint not null,
    FOREIGN KEY(parent_classe_id) REFERENCES classe(id) on delete set null,
    FOREIGN KEY(diagrama_id) REFERENCES diagrama(id) on delete cascade
) ENGINE=INNODB;

create table relacao_classe (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    source_classe_id bigint not null,
    target_classe_id bigint not null,
    relation varchar(50) not null,
    source_cardinality varchar(10),
    target_cardinality varchar(10),
    FOREIGN KEY(source_classe_id) REFERENCES classe(id) on delete cascade,
    FOREIGN KEY(target_classe_id) REFERENCES classe(id) on delete cascade
) ENGINE=INNODB;

create table tipo (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    kind int not null default 1, -- 1 for native and 0 for classe
    native_tipo varchar(50),
    classe_id bigint,
    FOREIGN KEY(classe_id) REFERENCES classe(id) on delete cascade
) ENGINE=INNODB;

create table atributo (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) not null,
    visibility varchar(20) not null default 'public',
    tipo_id bigint not null,
    classe_id bigint not null,
    FOREIGN KEY(classe_id) REFERENCES classe(id) on delete cascade,
    FOREIGN KEY(tipo_id) REFERENCES tipo(id) on delete cascade
) ENGINE=INNODB;

create table operacao (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) not null,
    visibility varchar(20) not null default 'public',
    tipo_id bigint not null,
    classe_id bigint not null,
    FOREIGN KEY(classe_id) REFERENCES classe(id) on delete cascade,
    FOREIGN KEY(tipo_id) REFERENCES tipo(id) on delete cascade
) ENGINE=INNODB;

create table parametro (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) not null,
    tipo_id bigint not null,
    operacao_id bigint not null,
    FOREIGN KEY(operacao_id) REFERENCES operacao(id) on delete cascade,
    FOREIGN KEY(tipo_id) REFERENCES tipo(id) on delete cascade
) ENGINE=INNODB;

insert into tipo(kind, native_tipo) values (1, 'string');
insert into tipo(kind, native_tipo) values (1, 'int');
insert into tipo(kind, native_tipo) values (1, 'float');
insert into tipo(kind, native_tipo) values (1, 'boolean');
