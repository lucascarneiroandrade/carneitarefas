package com.tarefas.service

import com.tarefas.controller.mapper.UsuarioMapper
import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.enums.ErrorsEnum
import com.tarefas.exception.NotFoundException
import com.tarefas.model.UsuarioModel
import com.tarefas.repository.UsuarioRepository
import org.springframework.stereotype.Service


@Service
class UsuarioService(
    var usuarioRepository: UsuarioRepository,
    var usuarioMapper: UsuarioMapper
) {


    fun criar(request: PostUsuarioRequest){
        val usuario = usuarioMapper.criarUsuario(request)

        usuarioRepository.save(usuario)
    }

    fun atualizar(id: Int, request: PutUsuarioRequest){
        val usuarioDB = listarPorId(id)

        usuarioRepository.save(usuarioMapper.atualizarUsuario(usuarioDB, request))
    }

    fun listar(id: Int): GetUsuarioResponse{
        val usuario = listarPorId(id)

        return usuarioMapper.listarUsuarioPorId(usuario)
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