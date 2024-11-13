package com.yourssu.ssumgo.common.storage.domain.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${classpath:security.jwt.token.expiration-time}")
    private val expirationTime: String
) {
    private val enc = Jwts.ENC.A256GCM
    private val secretKey = enc.key().build()

    fun generateTokenWithId(studentId: Long): String {
        val claims = Jwts.claims()
            .add("id", studentId.toString())
            .build()
        return generateToken(claims)
    }

    private fun generateToken(claims: Claims): String {
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date(Date().time + expirationTime.toLong()))
            .encryptWith(secretKey, enc)
            .compact()
    }
}