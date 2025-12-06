package com.tarefas.controller

import com.tarefas.request.PostUsuarioRequest
import com.tarefas.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class UsuarioController(
    val usuarioService: UsuarioService
) {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun criar(@RequestBody request: PostUsuarioRequest){
        usuarioService.criar(request)
    }

}