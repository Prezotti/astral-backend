create table Compra(

    id bigint unsigned not null auto_increment,
    data datetime not null,
    cliente varchar(255) not null,
    telefone varchar(255) not null,
    endereco varchar(255) not null,
    formaPagamento varchar(255) not null,
    opcaoRecebimento varchar(255) not null,
    doacao float unsigned,
    observacoes varchar(255),
    valorTotal float unsigned not null,
    feira_id bigint unsigned not null,

    primary key(id),
    constraint fk_compra_feira_id foreign key(feira_id) references Feira(id)

);