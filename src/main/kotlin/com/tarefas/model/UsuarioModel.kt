package com.tarefas.model

import com.tarefas.enums.Profile
import jakarta.persistence.*


@Entity(name = "usuario")
data class UsuarioModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    var nome: String,

    var email: String,

    var senha: String,

    @CollectionTable(name = "usuario_roles", joinColumns = [JoinColumn(name = "usuario_id")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var roles: Set<Profile> = setOf()




)
