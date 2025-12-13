package com.tarefas.controller.request

import com.tarefas.model.enums.TarefaStatusEnum

data class PatchStatusTarefaItemRequest(

    val id: Int,
    val status: TarefaStatusEnum

)
