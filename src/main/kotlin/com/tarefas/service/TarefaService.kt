package com.tarefas.service

import com.tarefas.controller.request.PatchStatusTarefaItemRequest
import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.response.GetTabelaTarefaResponse
import com.tarefas.enums.ErrorsEnum
import com.tarefas.enums.TarefaStatusEnum
import com.tarefas.exception.NotFoundException
import com.tarefas.exception.NotPermittedException
import com.tarefas.mapper.TarefaMapper
import com.tarefas.model.TarefaModel
import com.tarefas.repository.TarefaRepository
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

    fun listarPorId(id: Int): TarefaModel{

        return tarefaRepository.findById(id)
            .orElseThrow{ NotFoundException(
                ErrorsEnum.TRFS001.message.format(id),
                ErrorsEnum.TRFS001.code) }
    }

    fun listarTabela(): MutableList<GetTabelaTarefaResponse> {

        val usuario = usuarioService.buscaUsuarioLogado()

        val listaTarefas: List<TarefaModel> = tarefaRepository.findByUsuarioId(usuario)

        return TarefaStatusEnum.entries.map { statusEnum ->

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
                message = ErrorsEnum.TRFS001.message,
                errorCode = ErrorsEnum.TRFS001.code)
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

    private fun validarPermissao(tarefa: TarefaModel){
        val usuarioLogado = usuarioService.buscaUsuarioLogado()

        if(tarefa.usuarioId != usuarioLogado){
            throw NotPermittedException(
                message = ErrorsEnum.TRFS031.message,
                errorCode = ErrorsEnum.TRFS031.code)
        }
    }


}