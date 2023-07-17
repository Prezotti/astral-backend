create table feira(

    id bigint unsigned not null auto_increment,
    aberta tinyint not null,
    data_abertura datetime not null,
    taxa_entrega float unsigned not null,
    valor_total float unsigned,
    total_entregas float unsigned,
    total_doacoes float unsigned,

    primary key(id)
);