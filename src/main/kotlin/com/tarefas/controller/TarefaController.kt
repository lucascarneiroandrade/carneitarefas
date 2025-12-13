package com.tarefas.controller

import com.tarefas.controller.request.PatchStatusTarefaItemRequest
import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.response.GetTabelaTarefaResponse
import com.tarefas.service.TarefaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("tarefa")
class TarefaController(
    private val tarefaService: TarefaService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@RequestBody request: PostTarefaRequest) {
        return tarefaService.criar(request)
    }

    @GetMapping("/listar-tabela")
    fun listarTabelaPorUsuario(): List<GetTabelaTarefaResponse>{
        return tarefaService.listarTabela()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Int){
        return tarefaService.deletar(id)
    }

    @PatchMapping("/atualizar-status-lote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizarStatusLote(@RequestBody request: List<PatchStatusTarefaItemRequest>){
        return tarefaService.atualizarStatusEmLote(request)
    }


}