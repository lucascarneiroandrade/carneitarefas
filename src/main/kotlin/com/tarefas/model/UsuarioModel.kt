package com.tarefas.model

import jakarta.persistence.*


@Entity(name = "usuario")
data class UsuarioModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    var nome: String,

    var email: String,

    var senha: String




)
