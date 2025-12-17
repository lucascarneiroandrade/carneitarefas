package com.tarefas.model.mapper

import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.request.PutTarefaRequest
import com.tarefas.controller.response.GetTarefaResponse
import com.tarefas.model.entity.Tarefa
import com.tarefas.model.entity.Usuario
import com.tarefas.model.enums.TarefaStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TarefaMapper{

    fun criarTarefa(request: PostTarefaRequest, usuario: Usuario): Tarefa{
        return Tarefa(
            id = null,
            descricao = request.descricao,
            status = TarefaStatus.A_FAZER,
            criadoEm = LocalDateTime.now(),
            atualizadoEm = null,
            usuarioId = usuario
        )
    }

    fun atualizarTarefa(tarefaDB: Tarefa, tarefaRQ: PutTarefaRequest): Tarefa{
        return Tarefa(
            id = tarefaDB.id,
            descricao = tarefaRQ.descricao ?: tarefaDB.descricao,
            status = tarefaDB.status,
            criadoEm = tarefaDB.criadoEm,
            atualizadoEm = LocalDateTime.now(),
            usuarioId = tarefaDB.usuarioId
        )
    }

    fun converterParaListagem(tarefaDB: Tarefa): GetTarefaResponse{
        return GetTarefaResponse(
            id = tarefaDB.id,
            descricao = tarefaDB.descricao
        )
    }
}