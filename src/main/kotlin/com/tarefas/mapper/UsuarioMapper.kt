package com.tarefas.mapper

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.model.UsuarioModel
import org.springframework.stereotype.Component

@Component
class UsuarioMapper{

    fun criarUsuario(request: PostUsuarioRequest): UsuarioModel{
        return UsuarioModel(
            id = null,
            nome = request.nome,
            email = request.email,
            senha = request.senha)
    }

    fun atualizarUsuario(usuarioDB: UsuarioModel, usuarioRQ: PutUsuarioRequest): UsuarioModel{
        return UsuarioModel(
            id = usuarioDB.id,
            nome = usuarioRQ.nome ?: usuarioDB.nome,
            email = usuarioRQ.email ?: usuarioDB.email,
            senha = usuarioDB.senha
        )
    }

    fun listarUsuarioPorId(usuarioDB: UsuarioModel): GetUsuarioResponse{
        return GetUsuarioResponse(
            id = usuarioDB.id,
            nome = usuarioDB.nome,
            email = usuarioDB.email
        )
    }

}