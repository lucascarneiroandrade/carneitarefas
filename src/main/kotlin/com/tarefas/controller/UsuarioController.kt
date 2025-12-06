package com.tarefas.controller

import com.tarefas.model.UsuarioModel
import com.tarefas.request.PostUsuarioRequest
import com.tarefas.request.PutUsuarioRequest
import com.tarefas.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("usuario")
class UsuarioController(
    val usuarioService: UsuarioService
) {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun criar(@RequestBody request: PostUsuarioRequest){
        usuarioService.criar(request)
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun atualizar(@PathVariable id: Int, @RequestBody request: PutUsuarioRequest){
        usuarioService.atualizar(id, request)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: Int): UsuarioModel{
        return usuarioService.listarPorId(id)
    }

}