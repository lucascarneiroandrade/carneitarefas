package com.tarefas.controller

import com.tarefas.extension.converterParaResponse
import com.tarefas.request.PostUsuarioRequest
import com.tarefas.request.PutUsuarioRequest
import com.tarefas.response.GetUsuarioResponse
import com.tarefas.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


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
    fun listarPorId(@PathVariable id: Int): GetUsuarioResponse{
        return usuarioService.listarPorId(id).converterParaResponse()
    }

}