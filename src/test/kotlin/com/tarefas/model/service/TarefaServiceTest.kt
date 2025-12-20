package com.tarefas.model.service

import com.tarefas.controller.request.PatchStatusTarefaItemRequest
import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.response.GetTarefaResponse
import com.tarefas.model.entity.Tarefa
import com.tarefas.model.enums.Errors
import com.tarefas.model.enums.TarefaStatus
import com.tarefas.model.exception.NotFoundException
import com.tarefas.model.helper.criarTarefaTeste
import com.tarefas.model.helper.criarUsuarioTeste
import com.tarefas.model.mapper.TarefaMapper
import com.tarefas.model.repository.TarefaRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


@ExtendWith(MockKExtension::class)
class TarefaServiceTest {

    @MockK
    lateinit var tarefaRepository: TarefaRepository

    @MockK
    lateinit var usuarioService: UsuarioService

    @MockK
    lateinit var tarefaMapper: TarefaMapper

    lateinit var tarefaService: TarefaService


    @BeforeEach
    fun setup(){
        tarefaService = TarefaService(
            tarefaRepository,
            usuarioService,
            tarefaMapper
        )
    }

    @AfterEach
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `deve criar tarefa para usuario logado`() {
        val request = mockk<PostTarefaRequest>()

        val usuario = criarUsuarioTeste(id = 1)
        val tarefa = criarTarefaTeste(usuario = usuario)

        every { usuarioService.buscaUsuarioLogado() } returns usuario
        every { tarefaMapper.criarTarefa(request, usuario) } returns tarefa
        every { tarefaRepository.save(tarefa) } returns tarefa

        tarefaService.criar(request)

        verify(exactly = 1) {
            usuarioService.buscaUsuarioLogado()
            tarefaMapper.criarTarefa(request, usuario)
            tarefaRepository.save(tarefa)
        }
    }

    @Test
    fun `deve retornar tarefa quando id existir`() {
        val usuario = criarUsuarioTeste(id = 1)
        val tarefa = criarTarefaTeste(id = 10, usuario = usuario)

        every { tarefaRepository.findById(10) } returns Optional.of(tarefa)

        val result = tarefaService.listarPorId(10)

        assertEquals(tarefa, result)

        verify(exactly = 1) {
            tarefaRepository.findById(10)
        }
    }

    @Test
    fun `deve lancar NotFoundException quando tarefa nao existir`() {
        every { tarefaRepository.findById(99) } returns Optional.empty()

        val exception = assertThrows(NotFoundException::class.java) {
            tarefaService.listarPorId(99)
        }

        assertEquals(
            Errors.TRFS001.message.format(99),
            exception.message
        )

        assertEquals(
            Errors.TRFS001.code,
            exception.errorCode
        )

        verify(exactly = 1) {
            tarefaRepository.findById(99)
        }
    }

    @Test
    fun `deve listar tarefas agrupadas por status`() {
        val usuario = criarUsuarioTeste(id = 1)

        val tarefaAFazer = criarTarefaTeste(
            id = 1,
            status = TarefaStatus.A_FAZER,
            usuario = usuario
        )

        val tarefaEmAndamento = criarTarefaTeste(
            id = 2,
            status = TarefaStatus.EM_ANDAMENTO,
            usuario = usuario
        )

        val tarefaFeito = criarTarefaTeste(
            id = 3,
            status = TarefaStatus.FEITO,
            usuario = usuario
        )

        val responseAFazer = mockk<GetTarefaResponse>()
        val responseEmAndamento = mockk<GetTarefaResponse>()
        val responseFeito = mockk<GetTarefaResponse>()

        every { usuarioService.buscaUsuarioLogado() } returns usuario
        every { tarefaRepository.findByUsuarioId(usuario) } returns listOf(tarefaAFazer, tarefaEmAndamento, tarefaFeito)
        every { tarefaMapper.converterParaListagem(tarefaAFazer) } returns responseAFazer
        every { tarefaMapper.converterParaListagem(tarefaEmAndamento) } returns responseEmAndamento
        every { tarefaMapper.converterParaListagem(tarefaFeito) } returns responseFeito


        val result = tarefaService.listarTabela()

        assertEquals(TarefaStatus.entries.size, result.size)

        verify(exactly = 1) {
            usuarioService.buscaUsuarioLogado()
            tarefaRepository.findByUsuarioId(usuario)
        }

        verify {
            tarefaMapper.converterParaListagem(tarefaAFazer)
            tarefaMapper.converterParaListagem(tarefaEmAndamento)
            tarefaMapper.converterParaListagem(tarefaFeito)
        }
    }

    @Test
    fun `deve atualizar status das tarefas em lote quando ids forem validos`() {
        val usuario = criarUsuarioTeste(id = 1)

        val tarefa1 = criarTarefaTeste(
            id = 1,
            status = TarefaStatus.A_FAZER,
            usuario = usuario
        )

        val tarefa2 = criarTarefaTeste(
            id = 2,
            status = TarefaStatus.A_FAZER,
            usuario = usuario
        )

        val request = listOf(
            PatchStatusTarefaItemRequest(id = 1, status = TarefaStatus.FEITO),
            PatchStatusTarefaItemRequest(id = 2, status = TarefaStatus.EM_ANDAMENTO)
        )

        every { usuarioService.buscaUsuarioLogado() } returns usuario
        every { tarefaRepository.findAllById(setOf(1, 2)) } returns listOf(tarefa1, tarefa2)
        every { tarefaRepository.saveAll(any<Iterable<Tarefa>>()) } returns listOf(tarefa1, tarefa2)

        tarefaService.atualizarStatusEmLote(request)

        assertEquals(TarefaStatus.FEITO, tarefa1.status)
        assertEquals(TarefaStatus.EM_ANDAMENTO, tarefa2.status)
        assertNotNull(tarefa1.atualizadoEm)
        assertNotNull(tarefa2.atualizadoEm)

        verify(exactly = 1) {
            usuarioService.buscaUsuarioLogado()
            tarefaRepository.findAllById(setOf(1, 2))
            tarefaRepository.saveAll(listOf(tarefa1, tarefa2))
        }
    }



}