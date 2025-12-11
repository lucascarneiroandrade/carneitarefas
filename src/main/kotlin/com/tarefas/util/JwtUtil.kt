package com.tarefas.util

import com.tarefas.exception.AuthenticationException
import io.jsonwebtoken.Claims
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

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        try{
            val key = Keys.hmacShaKeyFor(secret!!.toByteArray())

            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch(ex: Exception){
            throw AuthenticationException("Token inválido", "999")
        }

    }

    fun getSubject(token: String): String {

        return getClaims(token).subject

    }
}