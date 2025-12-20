package com.tarefas.model.helper

import com.tarefas.model.entity.Usuario
import com.tarefas.model.enums.Role
import java.util.UUID

fun criarUsuarioTeste(
    id: Int? = null,
    nome: String = "Nome Usuário Teste",
    email: String = "${UUID.randomUUID()}@email.com",
    senha: String = "123teste"
) = Usuario(
    id = id,
    nome = nome,
    email = email,
    senha = senha,
    roles = setOf(Role.USUARIO)
)