package com.yourssu.ssumgo.common.implement.support.soomsil

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Component
interface AuthClient {
    @PostMapping
    fun signIn(@RequestBody request: SignInClientRequest): TokenResponse

    @PostMapping
    fun getRefreshToken(@RequestBody request: RefreshTokenClientRequest): TokenResponse
}

data class SignInClientRequest(
    val email: String,
    val password: String,
)

data class RefreshTokenClientRequest(
    val refreshToken: String
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
