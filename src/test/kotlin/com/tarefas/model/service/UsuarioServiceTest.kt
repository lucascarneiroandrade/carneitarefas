package com.tarefas.model.service

import com.tarefas.controller.request.PostUsuarioRequest
import com.tarefas.controller.request.PutUsuarioRequest
import com.tarefas.model.enums.Errors
import com.tarefas.model.enums.Role
import com.tarefas.model.exception.NotFoundException
import com.tarefas.model.helper.criarUsuarioTeste
import com.tarefas.model.mapper.UsuarioMapper
import com.tarefas.model.repository.UsuarioRepository
import com.tarefas.model.util.SecurityUtils
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @AfterEach
    fun tearDown() {
        unmockkAll()
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

        verify (exactly = 1) {
            usuarioMapper.criarUsuario(request)
            passwordEncoder.encode(rawPassword)
            usuarioRepository.save(match {
                it.senha == "senhaHash" && it.roles.contains(Role.USUARIO)
            })
        }
    }

    @Test
    fun `deve atualizar usuario existente`() {
        val id = 1
        val request = mockk<PutUsuarioRequest>()

        val usuarioDB = criarUsuarioTeste().copy(id = id)
        val usuarioAtualizado = usuarioDB.copy(nome = "Nome Atualizado")

        every { usuarioRepository.findById(id) } returns Optional.of(usuarioDB)
        every { usuarioMapper.atualizarUsuario(usuarioDB, request) } returns usuarioAtualizado
        every { usuarioRepository.save(usuarioAtualizado) } returns usuarioAtualizado

        usuarioService.atualizar(id, request)

        verify(exactly = 1) {
            usuarioRepository.findById(id)
            usuarioMapper.atualizarUsuario(usuarioDB, request)
            usuarioRepository.save(usuarioAtualizado)
        }
    }

    @Test
    fun `deve retornar usuario quando id existir`() {
        val id = 1
        val usuario = criarUsuarioTeste().copy(id = id)

        every { usuarioRepository.findById(id) } returns Optional.of(usuario)

        val result = usuarioService.listarPorId(id)

        assertEquals(usuario, result)

        verify(exactly = 1) {
            usuarioRepository.findById(id)
        }
    }
    @Test
    fun `deve lancar NotFoundException quando usuario nao existir`() {
        val id = 99

        every { usuarioRepository.findById(id) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            usuarioService.listarPorId(id)
        }

        assertEquals(
            Errors.TRFS002.message.format(id),
            exception.message
        )

        verify(exactly = 1) {
            usuarioRepository.findById(id)
        }
    }

    @Test
    fun `deve retornar usuario logado`() {
        mockkObject(SecurityUtils)

        val id = 1
        val usuario = criarUsuarioTeste().copy(id = id)

        every { SecurityUtils.getId() } returns id.toString()
        every { usuarioRepository.findById(id) } returns Optional.of(usuario)

        val result = usuarioService.buscaUsuarioLogado()

        assertEquals(usuario, result)

        verify(exactly = 1) {
            SecurityUtils.getId()
            usuarioRepository.findById(id)
        }
    }

    @Test
    fun `deve lancar NotFoundException quando usuario logado nao existir`() {
        mockkObject(SecurityUtils)

        val id = 99

        every { SecurityUtils.getId() } returns id.toString()
        every { usuarioRepository.findById(id) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            usuarioService.buscaUsuarioLogado()
        }

        assertEquals(
            Errors.TRFS002.message.format(id),
            exception.message
        )

        verify(exactly = 1) {
            SecurityUtils.getId()
            usuarioRepository.findById(id)
        }
    }
}