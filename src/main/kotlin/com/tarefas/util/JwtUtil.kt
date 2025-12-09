package com.tarefas.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    fun generateToken(id: Int): String {
        val key = Keys.hmacShaKeyFor(secret!!.toByteArray())
        return Jwts.builder()
            .subject(id.toString())
            .expiration(Date(System.currentTimeMillis() + expiration!!))
            .signWith(key)
            .compact()
    }
}