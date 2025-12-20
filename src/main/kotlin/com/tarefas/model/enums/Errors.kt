package com.tarefas.model.enums

enum class Errors(val code: String, val message: String) {

    TRFS000("TRFS-000", "Requisição inválida"),
    TRFS001("TRFS-001", "Tarefa [%s] não existe!"),
    TRFS002("TRFS-002", "Usuário [%s] não existe!"),
    TRFS030("TRFS-030", "Usuário não autorizado!"),
    TRFS031("TRFS-031", "Acesso negado!"),
    TRFS032("TRFS-032", "Falha ao se autenticar!"),
    TRFS033("TRFS-033", "Usuário não encontrado"),
    TRFS034("TRFS-034", "Token Inválido")
}