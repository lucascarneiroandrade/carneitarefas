package com.tarefas.controller.response

data class GetTabelaTarefaResponse(
    var status: String,
    var listaItens: MutableList<GetTarefaResponse> = mutableListOf()

)