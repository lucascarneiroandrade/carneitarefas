package com.tarefas.controller.security

import com.tarefas.model.enums.Errors
import com.tarefas.model.exception.AuthenticationException
import com.tarefas.model.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorization = request.getHeader("Authorization")

        if (authorization != null && authorization.startsWith("Bearer ")) {
            val token = authorization.removePrefix("Bearer ").trim()

            try {
                val auth = getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            } catch (ex: AuthenticationException) {
                SecurityContextHolder.clearContext()
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.message
                )
                return
            }
        }

        chain.doFilter(request, response)
    }
    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {

        if (!jwtUtil.isValidToken(token)) {
            throw AuthenticationException(Errors.TRFS034.message, Errors.TRFS034.code)
        }

        val subject = jwtUtil.getSubject(token)
        val user = userDetails.loadUserByUsername(subject)

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

}