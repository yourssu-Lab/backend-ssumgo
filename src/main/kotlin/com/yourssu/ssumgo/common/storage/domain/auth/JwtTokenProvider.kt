package com.yourssu.ssumgo.common.storage.domain.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.token.expiration-time}")
    private val expirationTime: String
) {
    private val enc = Jwts.ENC.A256GCM
    private val secretKey = enc.key().build()

    companion object {
        const val BEARER_FORMAT = "Bearer "
        const val STUDENT_SIGN = "id"
    }

    fun generateTokenWithId(studentId: Long): String {
        val claims = Jwts.claims()
            .add(STUDENT_SIGN, studentId.toString())
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

    fun extractStudentId(token: String): Long {
        println(parseToken(trimBearer(token))[STUDENT_SIGN])
        return parseToken(trimBearer(token))[STUDENT_SIGN].toString().toLong()

    }

    private fun trimBearer(token: String): String {
        if (!token.startsWith(BEARER_FORMAT)) {
            throw IllegalArgumentException("Invalid token: No Bearer")
        }
        return token.replace(BEARER_FORMAT, "")
    }

    private fun parseToken(token: String): Claims {
        println(Jwts.parser()
            .decryptWith(secretKey)
            .build()
            .parseEncryptedClaims(token)
            .payload)
        return Jwts.parser()
            .decryptWith(secretKey)
            .build()
            .parseEncryptedClaims(token)
            .payload
            ?: throw IllegalArgumentException("Invalid token")
    }
}