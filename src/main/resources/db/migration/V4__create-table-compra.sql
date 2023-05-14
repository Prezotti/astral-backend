create table compra(

    id bigint unsigned not null auto_increment,
    data datetime not null,
    cliente varchar(255) not null,
    telefone varchar(255) not null,
    endereco varchar(255) not null,
    forma_pagamento varchar(255) not null,
    opcao_recebimento varchar(255) not null,
    doacao float unsigned,
    observacoes varchar(255),
    valor_total float unsigned not null,
    feira_id bigint unsigned not null,

    primary key(id),
    constraint fk_compra_feira_id foreign key(feira_id) references feira(id)

);