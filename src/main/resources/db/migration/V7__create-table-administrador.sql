create table administrador(

    id bigint unsigned not null auto_increment,

    primary key(id),
    constraint fk_administrador_usuario_id foreign key(id) references usuario(id)
);