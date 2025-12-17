package com.tarefas.model.service

import com.tarefas.controller.request.PatchStatusTarefaItemRequest
import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.response.GetTabelaTarefaResponse
import com.tarefas.model.entity.Tarefa
import com.tarefas.model.enums.Errors
import com.tarefas.model.enums.TarefaStatus
import com.tarefas.model.exception.NotFoundException
import com.tarefas.model.exception.NotPermittedException
import com.tarefas.model.mapper.TarefaMapper
import com.tarefas.model.repository.TarefaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class TarefaService(
    private val tarefaRepository: TarefaRepository,
    private val usuarioService: UsuarioService,
    private val tarefaMapper: TarefaMapper
) {


    fun criar(request: PostTarefaRequest){
        val usuario = usuarioService.buscaUsuarioLogado()
        val tarefa = tarefaMapper.criarTarefa(request, usuario)

        tarefaRepository.save(tarefa)
    }

    fun listarPorId(id: Int): Tarefa{

        return tarefaRepository.findById(id)
            .orElseThrow{ NotFoundException(
                Errors.TRFS001.message.format(id),
                Errors.TRFS001.code) }
    }

    fun listarTabela(): MutableList<GetTabelaTarefaResponse> {

        val usuario = usuarioService.buscaUsuarioLogado()

        val listaTarefas: List<Tarefa> = tarefaRepository.findByUsuarioId(usuario)

        return TarefaStatus.entries.map { statusEnum ->

            val tarefasDoStatus = listaTarefas
                .filter { it.status == statusEnum }
                .map { tarefaMapper.converterParaListagem(it) }
                .toMutableList()

            GetTabelaTarefaResponse(
                status = statusEnum.toString(),
                listaItens = tarefasDoStatus
            )

        }.toMutableList()
    }

    fun deletar(id: Int) {
        val tarefa = listarPorId(id)
        validarPermissao(tarefa)

        tarefaRepository.delete(tarefa)
    }

    @Transactional
    fun atualizarStatusEmLote(request: List<PatchStatusTarefaItemRequest>) {

        val ids = request.map { it.id }.toSet()

        val tarefas = tarefaRepository.findAllById(ids).toList()

        if(tarefas.size != ids.size){
            throw NotFoundException(
                message = Errors.TRFS001.message,
                errorCode = Errors.TRFS001.code)
        }

        val agora = LocalDateTime.now()
        val statusPorId = request.associate { it.id to it.status }

        tarefas.forEach {
            validarPermissao(it)
            it.status = statusPorId[it.id]!!
            it.atualizadoEm = agora
        }
        tarefaRepository.saveAll(tarefas)
    }

    private fun validarPermissao(tarefa: Tarefa){
        val usuarioLogado = usuarioService.buscaUsuarioLogado()

        if(tarefa.usuarioId != usuarioLogado){
            throw NotPermittedException(
                message = Errors.TRFS031.message,
                errorCode = Errors.TRFS031.code)
        }
    }


}