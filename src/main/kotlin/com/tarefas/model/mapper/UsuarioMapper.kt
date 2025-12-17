package com.tarefas.model.mapper

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.model.entity.Usuario
import org.springframework.stereotype.Component

@Component
class UsuarioMapper{

    fun criarUsuario(request: PostUsuarioRequest): Usuario{
        return Usuario(
            id = null,
            nome = request.nome,
            email = request.email,
            senha = request.senha)
    }

    fun atualizarUsuario(usuarioDB: Usuario, usuarioRQ: PutUsuarioRequest): Usuario{
        return Usuario(
            id = usuarioDB.id,
            nome = usuarioRQ.nome ?: usuarioDB.nome,
            email = usuarioRQ.email ?: usuarioDB.email,
            senha = usuarioDB.senha
        )
    }

    fun listarUsuarioPorId(usuarioDB: Usuario): GetUsuarioResponse{
        return GetUsuarioResponse(
            id = usuarioDB.id,
            nome = usuarioDB.nome,
            email = usuarioDB.email
        )
    }

}