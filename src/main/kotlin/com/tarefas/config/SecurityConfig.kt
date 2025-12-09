package com.tarefas.config

import com.tarefas.repository.UsuarioRepository
import com.tarefas.security.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val usuarioRepository: UsuarioRepository
) {

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/usuario",
        "/login"
    )

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {

        val authFilter = AuthenticationFilter(usuarioRepository)
        authFilter.setAuthenticationManager(authenticationManager)
        authFilter.setFilterProcessesUrl("/login")

        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            }
        http.addFilter(authFilter)

        return http.build()
    }
}