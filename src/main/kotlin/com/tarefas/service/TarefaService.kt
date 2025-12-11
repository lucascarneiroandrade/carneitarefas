package com.tarefas.service

import com.tarefas.controller.mapper.TarefaMapper
import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.request.PutTarefaRequest
import com.tarefas.controller.response.GetTabelaTarefaResponse
import com.tarefas.controller.response.GetTarefaResponse
import com.tarefas.enums.ErrorsEnum
import com.tarefas.enums.TarefaStatusEnum
import com.tarefas.exception.NotFoundException
import com.tarefas.model.TarefaModel
import com.tarefas.repository.TarefaRepository
import org.springframework.stereotype.Service


@Service
class TarefaService(
    private val tarefaRepository: TarefaRepository,
    private val usuarioService: UsuarioService,
    private val tarefaMapper: TarefaMapper
) {


    fun criar(request: PostTarefaRequest){
        val usuario = usuarioService.listarPorId(request.usuarioId)
        val tarefa = tarefaMapper.criarTarefa(request, usuario)

        tarefaRepository.save(tarefa)
    }

    fun atualizar(id: Int, request: PutTarefaRequest){
        val tarefaDB = listarPorId(id)
        val tarefaUP = tarefaMapper.atualizarTarefa(tarefaDB, request)

        tarefaRepository.save(tarefaUP)
    }

    fun listar(id: Int): GetTarefaResponse{
        val tarefa = listarPorId(id)

        return tarefaMapper.converterParaListagem(tarefa)
    }

    fun listarPorId(id: Int): TarefaModel{

        return tarefaRepository.findById(id)
            .orElseThrow{ NotFoundException(
                ErrorsEnum.TRFS001.message.format(id),
                ErrorsEnum.TRFS001.code) }
    }

    fun listarPorUsuario(id: Int): List<GetTarefaResponse> {
        val usuario = usuarioService.listarPorId(id)

        return tarefaRepository.findByUsuarioId(usuario).map { tarefaMapper.converterParaListagem(it) }
    }

    fun listarTabelaPorUsuario(id: Int): MutableList<GetTabelaTarefaResponse> {
        val usuario = usuarioService.listarPorId(id)

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

        tarefaRepository.delete(listarPorId(id))
    }


}