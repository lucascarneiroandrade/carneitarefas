package com.tarefas.model.repository

import com.tarefas.model.entity.UsuarioModel
import org.springframework.data.repository.CrudRepository

interface UsuarioRepository: CrudRepository<UsuarioModel, Int> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UsuarioModel
}