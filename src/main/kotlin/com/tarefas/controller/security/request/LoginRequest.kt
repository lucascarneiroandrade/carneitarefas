package com.tarefas.controller.security.request

data class LoginRequest (
    val email: String,
    val senha: String
){
}