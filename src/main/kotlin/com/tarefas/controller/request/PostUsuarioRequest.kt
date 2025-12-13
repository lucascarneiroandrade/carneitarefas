package com.tarefas.controller.request

import com.tarefas.model.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostUsuarioRequest(

    @field:NotEmpty(message = "Nome deve ser informado!")
    var nome: String,

    @field:Email(message = "E-mail deve ser válido!")
    @EmailAvailable
    var email: String,

    @field:NotEmpty(message = "Senha deve ser informada!")
    var senha: String


)
