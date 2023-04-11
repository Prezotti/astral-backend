create table Feira(

    id bigint unsigned not null auto_increment,
    aberta tinyint not null,
    dataAbertura datetime not null,
    valorTotal float unsigned,

    primary key(id)
);