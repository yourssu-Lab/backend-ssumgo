package com.yourssu.ssumgo.common.application.domain.auth

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.business.domain.auth.AuthService
import com.yourssu.ssumgo.common.business.domain.auth.SignInCommand
import com.yourssu.ssumgo.common.business.domain.auth.SignInResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<Response<SignInResponse>> {
        val response = authService.signIn(request.toCommand())
        return ResponseEntity.ok(Response(result = response))
    }

    @PostMapping("/refresh")
    fun issueRefreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<Response<SignInResponse>> {
        val response = authService.issueRefreshToken(request)
        return ResponseEntity.ok(Response(result = response))
    }
}

data class SignInRequest(
    val email: String,
    val password: String
) {
    fun toCommand(): SignInCommand {
        return SignInCommand(
            email = email,
            password = password
        )
    }
}

data class RefreshTokenRequest(
    val refreshToken: String
)
