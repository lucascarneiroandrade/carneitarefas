package com.tarefas.controller.request

import com.tarefas.enums.TarefaStatusEnum

data class PatchStatusTarefaItemRequest(

    val id: Int,
    val status: TarefaStatusEnum

)
