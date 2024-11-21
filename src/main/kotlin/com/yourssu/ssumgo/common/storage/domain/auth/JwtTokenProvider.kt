package com.yourssu.ssumgo.common.storage.domain.auth

import com.yourssu.ssumgo.common.application.domain.common.UnauthorizedException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
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
        return parseToken(trimBearer(token))[STUDENT_SIGN].toString().toLong()

    }

    private fun trimBearer(token: String): String {
        if (!token.startsWith(BEARER_FORMAT)) {
            throw UnAuthorizedTokenException()
        }
        return token.replace(BEARER_FORMAT, "")
    }

    private fun parseToken(token: String): Claims {
        try {
            return Jwts.parser()
                .decryptWith(secretKey)
                .build()
                .parseEncryptedClaims(token)
                .payload
        } catch (e: MalformedJwtException) {
            throw UnAuthorizedTokenException()
        }
    }
}

class UnAuthorizedTokenException : UnauthorizedException(message = "유효하지 않은 토큰입니다.(Bearer *)")
