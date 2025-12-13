package com.tarefas.model.exception

class NotPermittedException(override val message: String, val errorCode: String): Exception() {
}