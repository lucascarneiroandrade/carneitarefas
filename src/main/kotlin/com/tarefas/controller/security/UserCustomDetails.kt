package com.tarefas.controller.security

import com.tarefas.model.entity.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val usuario: Usuario): UserDetails {
    val id: Int = usuario.id!!
    override fun getAuthorities(): Collection<GrantedAuthority> =
        usuario.roles.map {
            SimpleGrantedAuthority("ROLE_${it.descricao}")
        }.toMutableList()
    override fun getPassword(): String = usuario.senha
    override fun getUsername(): String = usuario.id.toString()
}