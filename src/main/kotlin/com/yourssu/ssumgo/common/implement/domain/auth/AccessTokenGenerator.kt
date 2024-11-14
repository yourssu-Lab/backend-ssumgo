package com.yourssu.ssumgo.common.implement.domain.auth

import com.yourssu.ssumgo.common.storage.domain.auth.JwtTokenProvider
import org.springframework.stereotype.Component

@Component
class AccessTokenGenerator(
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun generateToken(id: Long): String {
        return jwtTokenProvider.generateTokenWithId(id)
    }
}