package com.tarefas.model.service

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.model.entity.Usuario
import com.tarefas.model.enums.Role
import com.tarefas.model.mapper.UsuarioMapper
import com.tarefas.model.repository.UsuarioRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class UsuarioServiceTest {

    @MockK
    private lateinit var usuarioRepository: UsuarioRepository

    @MockK
    private lateinit var usuarioMapper: UsuarioMapper

    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var usuarioService: UsuarioService

    @BeforeEach
    fun setup() {
        usuarioService = UsuarioService(
            usuarioRepository,
            usuarioMapper,
            passwordEncoder
        )
    }

    @Test
    fun `deve criar usuario com senha criptografada e role padrao`() {
        val request = mockk<PostUsuarioRequest>()
        val usuario = criarUsuarioTeste().copy(roles = emptySet())
        val usuarioCriptografado = usuario.copy(senha = "senhaHash")
        val rawPassword = usuario.senha

        every { usuarioMapper.criarUsuario(request) } returns usuario
        every { passwordEncoder.encode(rawPassword) } returns "senhaHash"
        every {
            usuarioRepository.save(match {
                it.senha == "senhaHash" && it.roles.contains(Role.USUARIO)
            })
        } returns usuarioCriptografado
        usuarioService.criar(request)

        verify {
            usuarioMapper.criarUsuario(request)
            passwordEncoder.encode(rawPassword)
            usuarioRepository.save(match {
                it.senha == "senhaHash" && it.roles.contains(Role.USUARIO)
            })
        }
    }

    fun criarUsuarioTeste(
        id: Int? = null,
        nome: String = "Nome Usuário Teste",
        email: String = "${UUID.randomUUID()}@email.com",
        senha: String = "123teste"
    ) = Usuario(
        id = id,
        nome = nome,
        email = email,
        senha = senha,
        roles = setOf(Role.USUARIO)
    )
}