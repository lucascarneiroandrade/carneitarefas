package com.tarefas.model.validation

import com.tarefas.model.service.UsuarioService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EmailAvailableValidator(var usuarioService: UsuarioService): ConstraintValidator<EmailAvailable, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty()){
            return false
        }
        return usuarioService.emailDisponivel(value)
    }
}