create table itemCompra(
    id bigint unsigned not null auto_increment,
    quantidade tinyint unsigned not null,
    preco_unitario float unsigned not null,
    produto_id bigint unsigned not null,
    compra_id bigint unsigned not null,

    primary key(id),
    constraint fk_itemCompra_produto_id foreign key(produto_id) references Produto(id),
    constraint fk_itemCompra_compra_id foreign key(compra_id) references Compra(id)

);