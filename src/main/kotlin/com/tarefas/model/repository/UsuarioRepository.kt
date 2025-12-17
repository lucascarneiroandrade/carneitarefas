package com.tarefas.model.repository

import com.tarefas.model.entity.Usuario
import org.springframework.data.repository.CrudRepository

interface UsuarioRepository: CrudRepository<Usuario, Int> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Usuario
}