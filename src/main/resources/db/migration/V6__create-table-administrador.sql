CREATE TABLE Administrador (
    id bigint unsigned not null auto_increment,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);