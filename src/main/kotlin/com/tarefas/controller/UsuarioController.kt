package com.tarefas.controller

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.model.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("usuario")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun criar(@RequestBody @Valid request: PostUsuarioRequest){
        usuarioService.criar(request)
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun atualizar(@PathVariable id: Int, @RequestBody request: PutUsuarioRequest){
        usuarioService.atualizar(id, request)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun listarPorId(@PathVariable id: Int): GetUsuarioResponse{
        return usuarioService.listar(id)
    }

}