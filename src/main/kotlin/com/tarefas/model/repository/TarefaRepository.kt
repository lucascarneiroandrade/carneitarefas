package com.tarefas.model.repository

import com.tarefas.model.entity.TarefaModel
import com.tarefas.model.entity.UsuarioModel
import org.springframework.data.repository.CrudRepository

interface TarefaRepository : CrudRepository<TarefaModel, Int> {

    fun findByUsuarioId(usuario: UsuarioModel): List<TarefaModel>


}