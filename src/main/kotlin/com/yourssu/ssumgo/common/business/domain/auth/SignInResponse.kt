package com.yourssu.ssumgo.common.business.domain.auth

data class SignInResponse(
    val studentId: Long,
    val accessToken: String,
    val refreshToken: String,
)