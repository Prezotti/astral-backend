create table Produtor(

    id bigint unsigned not null auto_increment,
    nome varchar(255) not null,
    telefone varchar(255) not null,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    disponivel tinyint not null,
    ativo tinyint not null,

    primary key(id)

);