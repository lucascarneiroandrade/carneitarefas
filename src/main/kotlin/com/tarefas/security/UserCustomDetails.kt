package com.tarefas.security

import com.tarefas.model.UsuarioModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val usuarioModel: UsuarioModel): UserDetails {
    val id: Int = usuarioModel.id!!
    override fun getAuthorities(): Collection<GrantedAuthority> =
        usuarioModel.roles.map {
            SimpleGrantedAuthority("ROLE_${it.descricao}") 
        }.toMutableList()
    override fun getPassword(): String = usuarioModel.senha
    override fun getUsername(): String = usuarioModel.id.toString()
}