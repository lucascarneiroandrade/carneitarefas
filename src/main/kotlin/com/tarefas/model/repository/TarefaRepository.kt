package com.tarefas.model.repository

import com.tarefas.model.entity.Tarefa
import com.tarefas.model.entity.Usuario
import org.springframework.data.repository.CrudRepository

interface TarefaRepository : CrudRepository<Tarefa, Int> {

    fun findByUsuarioId(usuario: Usuario): List<Tarefa>


}