package com.tarefas.controller.security

import com.tarefas.model.enums.Errors
import com.tarefas.model.exception.AuthenticationException
import com.tarefas.model.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val usuarioRepository: UsuarioRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val usuario = usuarioRepository.findById(id.toInt())
            .orElseThrow{
                (AuthenticationException(Errors.TRFS033.message, Errors.TRFS033.code))
            }
        return UserCustomDetails(usuario)
    }
}