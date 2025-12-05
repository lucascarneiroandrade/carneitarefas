package com.tarefas.model

import com.tarefas.enums.TarefaStatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "tarefa")
data class TarefaModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var descricao: String,

    @Enumerated(EnumType.STRING)
    var status: TarefaStatusEnum,

    var criadoEm: LocalDateTime,

    var atualizadoEm: LocalDateTime?


)
