create table Produto (

        id bigint not null auto_increment,
        descricao varchar(255) not null,
        preco float not null,
        imagem varchar(255) not null,
        categoria varchar(255) not null,
        produtor_id bigint not null,
        disponivel tinyint not null,
        ativo tinyint not null,

        primary key(id),
        constraint fk_produto_produtor_id foreign key(produtor_id) references Produtor(id),
)