package com.tarefas.security

import com.tarefas.util.JwtUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tarefas.controller.request.LoginRequest
import com.tarefas.exception.AuthenticationException
import com.tarefas.repository.UsuarioRepository
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
            throw AuthenticationException("Falha ao se autenticar", "000")
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