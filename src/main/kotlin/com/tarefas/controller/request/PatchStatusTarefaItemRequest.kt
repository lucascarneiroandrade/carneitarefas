package com.tarefas.controller.request

import com.tarefas.model.enums.TarefaStatus

data class PatchStatusTarefaItemRequest(

    val id: Int,
    val status: TarefaStatus

)
