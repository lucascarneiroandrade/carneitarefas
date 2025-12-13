package com.tarefas.exception

class NotPermittedException(override val message: String, val errorCode: String): Exception() {
}