package com.tarefas.controller

import com.tarefas.controller.request.PostTarefaRequest
import com.tarefas.controller.request.PutTarefaRequest
import com.tarefas.controller.response.GetTarefaResponse
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizar(@PathVariable id: Int, @RequestBody request: PutTarefaRequest){
        return tarefaService.atualizar(id, request)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: Int): GetTarefaResponse{
        return tarefaService.listar(id)
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