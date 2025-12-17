package com.tarefas.model.entity

import com.tarefas.model.enums.TarefaStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "tarefa")
data class Tarefa(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var descricao: String,

    @Enumerated(EnumType.STRING)
    var status: TarefaStatus,

    var criadoEm: LocalDateTime,

    var atualizadoEm: LocalDateTime?,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    var usuarioId: Usuario


)
