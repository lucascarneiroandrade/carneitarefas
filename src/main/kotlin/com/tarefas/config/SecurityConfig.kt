package com.tarefas.config

import com.tarefas.enums.Role
import com.tarefas.repository.UsuarioRepository
import com.tarefas.security.AuthenticationFilter
import com.tarefas.security.AuthorizationFilter
import com.tarefas.service.UserDetailsCustomService
import com.tarefas.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val usuarioRepository: UsuarioRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) {

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/usuario",
        "/login"
    )

    private val ADMIN_MATCHERS = arrayOf(
        "/admin/**"
    )
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        // 1. Configurar o AuthenticationManger
        val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authManagerBuilder
            .userDetailsService(userDetails)
            .passwordEncoder(bCryptPasswordEncoder())

        val authenticationManager = authManagerBuilder.build()

        // 2. Criar e configurar o filtro
        val authFilterUserRepository = AuthenticationFilter(usuarioRepository, jwtUtil)
        authFilterUserRepository.setAuthenticationManager(authenticationManager)
        authFilterUserRepository.setFilterProcessesUrl("/login")

        // 3. Configuração do HttpSecurity
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                    .requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.descricao)
                    .anyRequest().authenticated()
            }

            .authenticationManager(authenticationManager)
            .addFilter(authFilterUserRepository)
            .addFilter(AuthorizationFilter(authenticationManager, userDetails, jwtUtil))

        return http.build()
    }
}