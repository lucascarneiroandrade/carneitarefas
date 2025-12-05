package com.tarefas.extension

import com.tarefas.TarefaStatusEnum
import com.tarefas.model.TarefaModel
import com.tarefas.request.PostTarefaRequest
import com.tarefas.request.PutTarefaRequest
import java.time.LocalDateTime

fun PostTarefaRequest.criarTarefa(): TarefaModel{
    return TarefaModel(null, descricao = this.descricao, TarefaStatusEnum.A_FAZER, LocalDateTime.now(), null)
}

fun TarefaModel.atualizarTarefa(request: PutTarefaRequest) {
    this.descricao = request.descricao
    this.atualizadoEm = LocalDateTime.now()
}
