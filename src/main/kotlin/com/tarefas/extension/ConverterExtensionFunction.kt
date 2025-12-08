package com.tarefas.extension

import com.tarefas.enums.TarefaStatusEnum
import com.tarefas.model.TarefaModel
import com.tarefas.model.UsuarioModel
import com.tarefas.response.GetTarefaResponse
import com.tarefas.request.PostTarefaRequest
import com.tarefas.request.PostUsuarioRequest
import com.tarefas.request.PutTarefaRequest
import com.tarefas.request.PutUsuarioRequest
import com.tarefas.response.GetUsuarioResponse
import java.time.LocalDateTime

fun PostTarefaRequest.criarTarefa(usuario: UsuarioModel): TarefaModel{
    return TarefaModel(
        id = null,
        descricao = this.descricao,
        status = TarefaStatusEnum.A_FAZER,
        criadoEm = LocalDateTime.now(),
        atualizadoEm = null,
        usuarioId = usuario)
}

fun PutTarefaRequest.atualizarTarefa(tarefaDB: TarefaModel): TarefaModel {
    return TarefaModel(
        id = tarefaDB.id,
        descricao = this.descricao ?: tarefaDB.descricao,
        status = tarefaDB.status,
        criadoEm = tarefaDB.criadoEm,
        atualizadoEm = LocalDateTime.now(),
        usuarioId = tarefaDB.usuarioId)
}

fun TarefaModel.converterParaResponse(): GetTarefaResponse{
    return GetTarefaResponse(
        id = this.id,
        descricao = this.descricao,
        status = this.status
    )
}

fun PostUsuarioRequest.criarUsuario(): UsuarioModel{
    return UsuarioModel(
        id = null,
        nome = this.nome,
        email = this.email,
        senha = this.senha)
}

fun PutUsuarioRequest.atualizarUsuario(usuarioDB: UsuarioModel): UsuarioModel{
    return UsuarioModel(
        id = usuarioDB.id,
        nome = this.nome ?: usuarioDB.nome,
        email = this.email ?: usuarioDB.email,
        senha = usuarioDB.senha
    )
}

fun UsuarioModel.converterParaResponse(): GetUsuarioResponse{
    return GetUsuarioResponse(
        id = this.id,
        nome = this.nome,
        email = this.email
    )
}
