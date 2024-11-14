package com.yourssu.ssumgo.common.implement.domain.auth

import com.yourssu.ssumgo.common.storage.domain.auth.JwtTokenProvider
import org.springframework.stereotype.Component

@Component
class AccessTokenExtractor(
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun extractStudentId(token: String): Long {
        return jwtTokenProvider.extractStudentId(token)
    }
}
