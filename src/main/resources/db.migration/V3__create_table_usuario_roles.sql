CREATE TABLE usuario_roles(
    usuario_id int not null,
    role varchar(50) not null,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);