package com.tarefas.model.exception

class NotFoundException(override val message: String, val errorCode: String): Exception() {
}