create table itemCompra(
    id bigint not null auto_increment,
    quantidade tinyint not null,
    precoUnitario float not null,
    produto_id bigint not null,
    compra_id bigint not null,

    primary key(id),
    constraint fk_itemCompra_produto_id foreign key(produto_id) references Produto(id),
    constraint fk_itemCompra_compra_id foreign key(compra_id) references Compra(id),

);