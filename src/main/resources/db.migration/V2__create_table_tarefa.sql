CREATE TABLE tarefa (
    id INT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    criado_em DATETIME NOT NULL,
    atualizado_em DATETIME,
    usuario_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_tarefa_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);