package com.tarefas.service

import com.tarefas.enums.ErrorsEnum
import com.tarefas.exception.NotFoundException
import com.tarefas.extension.atualizarUsuario
import com.tarefas.extension.criarUsuario
import com.tarefas.model.UsuarioModel
import com.tarefas.repository.UsuarioRepository
import com.tarefas.request.PostUsuarioRequest
import com.tarefas.request.PutUsuarioRequest
import org.springframework.stereotype.Service


@Service
class UsuarioService(
    var usuarioRepository: UsuarioRepository
) {


    fun criar(request: PostUsuarioRequest){
        val usuario = request.criarUsuario()

        usuarioRepository.save(usuario)
    }

    fun atualizar(id: Int, request: PutUsuarioRequest){
        val usuarioDB = listarPorId(id)
        val usuarioRQ = request.atualizarUsuario(usuarioDB)

        usuarioRepository.save(usuarioRQ)
    }

    fun listarPorId(id: Int): UsuarioModel{

        return usuarioRepository.findById(id)
            .orElseThrow{ NotFoundException(
                ErrorsEnum.TRFS002.message.format(id),
                ErrorsEnum.TRFS002.code) }
    }

    fun emailDisponivel(email: String): Boolean {
        return !usuarioRepository.existsByEmail(email)

    }

}