package com.tarefas.response

data class ErrorResponse(

    var httpCode: Int,
    var message: String,
    var internalCode: String,
    var errors: List<FieldErrorResponse>?

)
