package com.tarefas.service

import com.tarefas.extension.atualizarTarefa
import com.tarefas.extension.criarTarefa
import com.tarefas.model.TarefaModel
import com.tarefas.repository.TarefaRepository
import com.tarefas.request.PostTarefaRequest
import com.tarefas.request.PutTarefaRequest
import org.springframework.stereotype.Service


@Service
class TarefaService(
    val tarefaRepository: TarefaRepository,
    val usuarioService: UsuarioService
) {


    fun criar(request: PostTarefaRequest){
        val usuario = usuarioService.listarPorId(request.usuarioId)
        val tarefa = request.criarTarefa(usuario)

        tarefaRepository.save(tarefa)
    }

    fun atualizar(id: Int, request: PutTarefaRequest){
        val tarefaDB = listarPorId(id)
        val tarefaRQ = request.atualizarTarefa(tarefaDB)

        tarefaRepository.save(tarefaRQ)
    }

    fun deletar(id: Int) {

        tarefaRepository.delete(listarPorId(id))
    }

    fun listarPorId(id: Int): TarefaModel{

        return tarefaRepository.findById(id)
            .orElseThrow{ RuntimeException("Tarefa $id não encontrada") }
    }


}