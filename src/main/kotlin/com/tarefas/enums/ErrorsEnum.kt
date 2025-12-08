package com.tarefas.enums

enum class ErrorsEnum(val code: String, val message: String) {

    TRFS001("TRFS-001", "Tarefa [%s] não existe!"),
    TRFS002("TRFS-002", "Usuário [%s] não existe!"),
    TRFS030("TRFS-030", "Requisição inválida")
}