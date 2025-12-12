package com.tarefas.service

import com.tarefas.controller.mapper.UsuarioMapper
import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.enums.ErrorsEnum
import com.tarefas.enums.Role
import com.tarefas.exception.NotFoundException
import com.tarefas.model.UsuarioModel
import com.tarefas.repository.UsuarioRepository
import com.tarefas.util.SecurityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UsuarioService(
    private var usuarioRepository: UsuarioRepository,
    private var usuarioMapper: UsuarioMapper,
    private val bCrypt: BCryptPasswordEncoder
) {


    fun criar(request: PostUsuarioRequest){
        val usuario = usuarioMapper.criarUsuario(request)
        val usuarioCopy = usuario.copy(

            roles = setOf(Role.USUARIO),
            senha = bCrypt.encode(usuario.senha)
        )

        usuarioRepository.save(usuarioCopy)
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

    fun buscaUsuarioLogado(): UsuarioModel{
        val id = SecurityUtils.getId()

        return listarPorId(id.toInt())
    }

}