package com.tarefas.repository

import com.tarefas.model.TarefaModel
import org.springframework.data.repository.CrudRepository

interface TarefaRepository : CrudRepository<TarefaModel, Int> {

    fun findByUsuarioId(usuarioId: Int): List<TarefaModel>


}