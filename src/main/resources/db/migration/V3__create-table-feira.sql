create table Feira(

    id bigint unsigned not null auto_increment,
    aberta tinyint not null,
    data_abertura datetime not null,
    valor_total float unsigned,

    primary key(id)
);