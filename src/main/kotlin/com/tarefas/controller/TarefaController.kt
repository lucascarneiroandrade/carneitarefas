package com.tarefas.controller

import com.tarefas.model.TarefaModel
import com.tarefas.response.GetTarefaResponse
import com.tarefas.request.PostTarefaRequest
import com.tarefas.request.PutTarefaRequest
import com.tarefas.service.TarefaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("tarefa")
class TarefaController(
    val tarefaService: TarefaService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@RequestBody request: PostTarefaRequest) {
        return tarefaService.criar(request)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizar(@PathVariable id: Int, @RequestBody request: PutTarefaRequest){
        return tarefaService.atualizar(id, request)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: Int): TarefaModel{
        return tarefaService.listarPorId(id)
    }

    @GetMapping("/listar-por-usuario/{id}")
    fun listarPorUsuario(@PathVariable id: Int): List<GetTarefaResponse>{
        return tarefaService.listarPorUsuario(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Int){
        return tarefaService.deletar(id)
    }


}