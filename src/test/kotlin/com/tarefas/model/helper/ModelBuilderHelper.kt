package com.tarefas.model.helper

import com.tarefas.model.entity.Tarefa
import com.tarefas.model.entity.Usuario
import com.tarefas.model.enums.Role
import com.tarefas.model.enums.TarefaStatus
import java.time.LocalDateTime
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

fun criarTarefaTeste(
    id: Int? = null,
    descricao: String = "Descrição Tarefa Teste",
    status: TarefaStatus = TarefaStatus.A_FAZER,
    usuario: Usuario
) = Tarefa(
    id = id,
    descricao = descricao,
    status = status,
    criadoEm = LocalDateTime.now(),
    atualizadoEm = null,
    usuarioId = usuario
)