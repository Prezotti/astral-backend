create table Compra(

    id bigint not null auto_increment,
    data date not null,
    cliente varchar(255) not null,
    telefone varchar(255) not null,
    endereco varchar(255) not null,
    formaPagamento varchar(255) not null,
    opcaoRecebimento varchar(255) not null,
    doacao float,
    observacoes varchar(255),
    valorTotal float not null,
    feira_id bigint not null,

    primary key(id),
    constraint fk_compra_feira_id foreign key(feira_id) references Feira(id),

);