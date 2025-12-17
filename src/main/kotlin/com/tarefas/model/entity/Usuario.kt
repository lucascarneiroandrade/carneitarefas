package com.tarefas.model.entity

import com.tarefas.model.enums.Role
import jakarta.persistence.*


@Entity(name = "usuario")
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    var nome: String,

    var email: String,

    var senha: String,

    @CollectionTable(name = "usuario_roles", joinColumns = [JoinColumn(name = "usuario_id")])
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var roles: Set<Role> = setOf()




)
