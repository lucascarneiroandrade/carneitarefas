package com.tarefas.controller.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tarefas.controller.security.request.LoginRequest
import com.tarefas.model.enums.Errors
import com.tarefas.model.exception.AuthenticationException
import com.tarefas.model.repository.UsuarioRepository
import com.tarefas.model.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    private val usuarioRepository: UsuarioRepository,
    private val jwtUtil: JwtUtil
): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val id = usuarioRepository.findByEmail(loginRequest.email)?.id
            val authToken = UsernamePasswordAuthenticationToken(
                id,
                loginRequest.senha
            )
            return authenticationManager.authenticate(authToken)
        } catch (ex: Exception) {
            throw AuthenticationException(Errors.TRFS032.message, Errors.TRFS032.code)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }


}