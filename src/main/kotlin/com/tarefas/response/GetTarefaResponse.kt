package com.tarefas.response

import com.tarefas.enums.TarefaStatusEnum

data class GetTarefaResponse(

    var id: Int?,
    var descricao: String?,
    var status : TarefaStatusEnum
)