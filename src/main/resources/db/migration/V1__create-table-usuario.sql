create table usuario(

    id bigint unsigned not null auto_increment,
    email varchar(255) not null,
    senha varchar(255) not null,

    primary key(id)
);