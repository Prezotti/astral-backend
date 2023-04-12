create table Produto (

        id bigint unsigned not null auto_increment,
        descricao varchar(255) not null,
        preco float unsigned not null,
        medida varchar(255),
        qtdEstoque int not null,
        imagem varchar(255) not null,
        categoria varchar(255) not null,
        produtor_id bigint unsigned not null,
        disponivel tinyint not null,
        ativo tinyint not null,

        primary key(id),
        constraint fk_produto_produtor_id foreign key(produtor_id) references Produtor(id)
);