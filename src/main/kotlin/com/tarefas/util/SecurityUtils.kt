package com.tarefas.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtils {

    fun getUsername(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Usuário não autenticado")

        return authentication.name
    }

    fun getUserDetails(): UserDetails {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Usuário não autenticado")

        return authentication.principal as UserDetails
    }
}