package com.yourssu.ssumgo.common.application.domain.auth

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.business.domain.auth.AuthService
import com.yourssu.ssumgo.common.business.domain.auth.SignInCommand
import com.yourssu.ssumgo.common.business.domain.auth.SignInResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
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
    fun signIn(@Valid @RequestBody request: SignInRequest): ResponseEntity<Response<SignInResponse>> {
        val response = authService.signIn(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).body(Response(result = response))
    }

    @PostMapping("/refresh")
    fun issueRefreshToken(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<Response<SignInResponse>> {
        val response = authService.issueRefreshToken(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(Response(result = response))
    }
}

data class SignInRequest(
    @NotBlank(message = "올바른 이메일 또는 비밀번호를 입력해주세요.")
    @SoongsilEmail
    val email: String,

    @NotBlank(message = "올바른 이메일 또는 비밀번호를 입력해주세요.")
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
    @NotBlank(message = "올바르지 않은 토큰입니다.")
    val refreshToken: String
)
