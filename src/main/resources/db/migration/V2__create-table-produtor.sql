create table Produtor(

    id bigint unsigned not null auto_increment,
    nome varchar(255) not null,
    telefone varchar(255) not null,
    disponivel tinyint not null,
    ativo tinyint not null,

    primary key(id),
    constraint fk_produtor_usuario_id foreign key(id) references Usuario(id)
);