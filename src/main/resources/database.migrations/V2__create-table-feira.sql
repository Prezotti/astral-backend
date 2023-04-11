create table Feira(

    id bigint not null auto_increment,
    aberta tinyint not null,
    dataAbertura date not null,
    valorTotal float,

    primary key(id)
);