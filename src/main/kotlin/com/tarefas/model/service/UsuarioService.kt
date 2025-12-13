package com.tarefas.model.service

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.controller.response.GetUsuarioResponse
import com.tarefas.model.entity.UsuarioModel
import com.tarefas.model.enums.Errors
import com.tarefas.model.enums.Role
import com.tarefas.model.exception.NotFoundException
import com.tarefas.model.mapper.UsuarioMapper
import com.tarefas.model.repository.UsuarioRepository
import com.tarefas.model.util.SecurityUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UsuarioService(
    private var usuarioRepository: UsuarioRepository,
    private var usuarioMapper: UsuarioMapper,
    private val passwordEncoder: PasswordEncoder
) {


    fun criar(request: PostUsuarioRequest){
        val usuario = usuarioMapper.criarUsuario(request)
        val usuarioCopy = usuario.copy(

            roles = setOf(Role.USUARIO),
            senha = passwordEncoder.encode(usuario.senha)
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
                Errors.TRFS002.message.format(id),
                Errors.TRFS002.code) }
    }

    fun emailDisponivel(email: String): Boolean {
        return !usuarioRepository.existsByEmail(email)

    }

    fun buscaUsuarioLogado(): UsuarioModel{
        val id = SecurityUtils.getId()

        return listarPorId(id.toInt())
    }

}