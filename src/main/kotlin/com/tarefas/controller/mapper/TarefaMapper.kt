package com.tarefas.controller.mapper

import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.request.PutTarefaRequest
import com.tarefas.controller.response.GetTarefaResponse
import com.tarefas.enums.TarefaStatusEnum
import com.tarefas.model.TarefaModel
import com.tarefas.service.UsuarioService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TarefaMapper(
    private val usuarioService: UsuarioService
) {

    fun criarTarefa(request: PostTarefaRequest): TarefaModel{
        return TarefaModel(
            id = null,
            descricao = request.descricao,
            status = TarefaStatusEnum.A_FAZER,
            criadoEm = LocalDateTime.now(),
            atualizadoEm = null,
            usuarioId = usuarioService.listarPorId(request.usuarioId)
        )
    }

    fun atualizarTarefa(tarefaDB: TarefaModel, tarefaRQ: PutTarefaRequest): TarefaModel{
        return TarefaModel(
            id = tarefaDB.id,
            descricao = tarefaRQ.descricao ?: tarefaDB.descricao,
            status = tarefaDB.status,
            criadoEm = tarefaDB.criadoEm,
            atualizadoEm = LocalDateTime.now(),
            usuarioId = tarefaDB.usuarioId
        )
    }

    fun converterParaListagem(tarefaDB: TarefaModel): GetTarefaResponse{
        return GetTarefaResponse(
            id = tarefaDB.id,
            descricao = tarefaDB.descricao,
            status = tarefaDB.status
        )
    }
}