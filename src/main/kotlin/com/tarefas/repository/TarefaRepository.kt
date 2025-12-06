package com.tarefas.repository

import com.tarefas.model.TarefaModel
import com.tarefas.model.UsuarioModel
import org.springframework.data.repository.CrudRepository

interface TarefaRepository : CrudRepository<TarefaModel, Int> {

    fun findByUsuarioId(usuario: UsuarioModel): List<TarefaModel>


}