package com.tarefas.repository

import com.tarefas.model.UsuarioModel
import org.springframework.data.repository.CrudRepository

interface UsuarioRepository: CrudRepository<UsuarioModel, Int> {

    fun existsByEmail(email: String): Boolean
}