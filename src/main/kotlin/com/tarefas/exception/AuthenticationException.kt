package com.tarefas.exception

class AuthenticationException(override val message: String, val errorCode: String): Exception() {
}