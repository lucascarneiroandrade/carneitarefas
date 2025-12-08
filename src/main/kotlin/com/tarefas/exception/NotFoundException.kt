package com.tarefas.exception

class NotFoundException(override val message: String, val errorCode: String): Exception() {
}