package com.yourssu.ssumgo.common.business.domain.auth

data class SignInCommand(
    val email: String,
    val password: String,
)
