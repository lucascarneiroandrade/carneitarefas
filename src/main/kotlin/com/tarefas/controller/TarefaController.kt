package com.tarefas.controller

import com.tarefas.extension.criarTarefa
import com.tarefas.model.TarefaModel
import com.tarefas.request.PostTarefaRequest
import com.tarefas.request.PutTarefaRequest
import com.tarefas.service.TarefaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("tarefa")
class TarefaController(
    val service: TarefaService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@RequestBody request: PostTarefaRequest) {
        return service.criar(request)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizar(@RequestBody request: PutTarefaRequest, @PathVariable id: Int){
        return service.atualizar(id, request)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: Int): TarefaModel{
        return service.listarPorId(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Int){
        return service.deletar(id)
    }


}