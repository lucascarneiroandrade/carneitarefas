package com.tarefas.config

import com.tarefas.enums.Role
import com.tarefas.repository.UsuarioRepository
import com.tarefas.security.AuthenticationFilter
import com.tarefas.security.AuthorizationFilter
import com.tarefas.service.UserDetailsCustomService
import com.tarefas.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig(
    private val usuarioRepository: UsuarioRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) {

    private val PUBLIC_MATCHERS = arrayOf(
        "/login",
        "/usuario"
    )

    private val SWAGGER_MATCHERS = arrayOf(
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**"
    )

    private val ADMIN_MATCHERS = arrayOf(
        "/admin/**"
    )


    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager{
        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
            builder
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder)

        return builder.build()
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager): SecurityFilterChain {


        http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*PUBLIC_MATCHERS
                    ).permitAll()
                    .requestMatchers(*SWAGGER_MATCHERS)
                    .permitAll()
                    .requestMatchers(*ADMIN_MATCHERS)
                    .hasAuthority(Role.ADMIN.descricao)
                    .anyRequest().authenticated()
            }
            .authenticationManager(authenticationManager)
            .addFilterBefore(
                AuthorizationFilter(authenticationManager, userDetails, jwtUtil),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilter(AuthenticationFilter(usuarioRepository, jwtUtil).apply {
                setAuthenticationManager(authenticationManager)
                setFilterProcessesUrl("/login")
            })


        return http.build()
    }

    @Bean
    fun corsConfig(): CorsFilter{
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}