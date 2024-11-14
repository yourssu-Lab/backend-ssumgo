package com.yourssu.ssumgo.common.business.domain.auth

import com.yourssu.ssumgo.common.application.domain.auth.RefreshTokenRequest
import com.yourssu.ssumgo.common.implement.domain.auth.AccessTokenGenerator
import com.yourssu.ssumgo.common.implement.support.soomsil.*
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val authClient: AuthClient,
    private val userClient: UserClient,
    private val accessTokenGenerator: AccessTokenGenerator,
    private val studentWriter: StudentWriter,
) {
    @Transactional
    fun signIn(command: SignInCommand): SignInResponse {
        val token: TokenResponse = authClient.signIn(
            SignInClientRequest(
                email = command.email,
                password = command.password
            )
        )
        return signInSsumgo(token)
    }

    @Transactional
    fun issueRefreshToken(request: RefreshTokenRequest): SignInResponse {
        val token = authClient.getRefreshToken(
            RefreshTokenClientRequest(
                request.refreshToken
            )
        )
        return signInSsumgo(token)
    }

    private fun signInSsumgo(token: TokenResponse): SignInResponse {
        val userResponse: UserResponse = userClient.getUser(accessTokenGenerator.formatingBearer(token.accessToken))
        val student = studentWriter.signIn(userResponse.toDomain())
        return SignInResponse(
            accessToken = accessTokenGenerator.generateToken(student.id!!),
            refreshToken = token.refreshToken,
            studentId = student.id,
        )
    }
}
