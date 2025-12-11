package com.tarefas.service

import com.tarefas.exception.AuthenticationException
import com.tarefas.repository.UsuarioRepository
import com.tarefas.security.UserCustomDetails
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
                (AuthenticationException("Usuário não encontrado", "999"))
            }
        return UserCustomDetails(usuario)
    }
}