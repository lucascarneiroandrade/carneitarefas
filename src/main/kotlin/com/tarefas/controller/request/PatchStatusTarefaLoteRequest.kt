package com.tarefas.controller.request

data class PatchStatusTarefaLoteRequest(

    val tarefas: List<PatchStatusTarefaItemRequest>

)
