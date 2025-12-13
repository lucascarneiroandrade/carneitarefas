package com.tarefas.model.exception

class AuthenticationException(override val message: String, val errorCode: String): Exception() {
}