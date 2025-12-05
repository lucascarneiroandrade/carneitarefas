package com.tarefas.service

import com.tarefas.extension.atualizarTarefa
import com.tarefas.model.TarefaModel
import com.tarefas.repository.TarefaRepository
import com.tarefas.request.PutTarefaRequest
import org.springframework.stereotype.Service


@Service
class TarefaService(
    val repository: TarefaRepository
) {


    fun criar(tarefa: TarefaModel){
        repository.save(tarefa)
    }

    fun atualizar(id: Int, request: PutTarefaRequest){
        val tarefaDB = repository.findById(id)
            .orElseThrow{ RuntimeException("Tarefa $id não encontrada") }

        tarefaDB.atualizarTarefa(request)
        repository.save(tarefaDB)

    }

    fun listarPorId(id: Int): TarefaModel{
        return repository.findById(id)
            .orElseThrow{ RuntimeException("Tarefa $id não encontrada") }
    }

    fun deletar(id: Int){
        return repository.delete(
            repository.findById(id)
                .orElseThrow{ RuntimeException("Tarefa $id não encontrada") })
    }


}