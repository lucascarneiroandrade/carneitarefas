package com.tarefas.model.entity

import com.tarefas.enums.TarefaStatusEnum
import jakarta.persistence.*
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

    var atualizadoEm: LocalDateTime?,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    var usuarioId: UsuarioModel


)
