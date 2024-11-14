package com.yourssu.ssumgo.common.business.domain.auth

import com.yourssu.ssumgo.common.application.domain.auth.RefreshTokenRequest
import com.yourssu.ssumgo.common.implement.support.soomsil.ProfileImageUrlsResponse
import com.yourssu.ssumgo.common.implement.support.soomsil.TokenResponse
import com.yourssu.ssumgo.common.implement.support.soomsil.UserResponse
import com.yourssu.ssumgo.common.storage.support.soomsil.AuthFeignClientImpl
import com.yourssu.ssumgo.common.storage.support.soomsil.SoomsilFeignClientImpl
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.bean.override.mockito.MockitoBean
import kotlin.test.assertEquals

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthServiceTest {

    @Autowired
    private lateinit var authService: AuthService

    @MockitoBean
    private lateinit var authClient: AuthFeignClientImpl

    @MockitoBean
    private lateinit var userClient: SoomsilFeignClientImpl

    @Test
    fun signIn() {
        `when`(authClient.signIn(any()))
            .thenReturn(TokenResponse("access.Token.sign", "refresh.Token.sign"))
        `when`(userClient.getUser(any()))
            .thenReturn(
                UserResponse(
                    email = "leo@soongsil.ac.kr",
                    nickName = "nickname",
                    profileImage = ProfileImageUrlsResponse(
                        smallUrl = "smallUrl",
                        midUrl = "profileUrl",
                        largeUrl = "largeUrl"
                    )
                ))
        val signInCommand = SignInCommand("leo@soongsil.ac.kr", "password")

        val response: SignInResponse = authService.signIn(signInCommand)

        assertEquals(1L, response.studentId)
    }

    @Test
    fun issueRefreshToken() {
        `when`(authClient.getRefreshToken(any()))
            .thenReturn(TokenResponse("access.Token.refresh", "refresh.Token.refresh"))
        `when`(userClient.getUser(any()))
            .thenReturn(
                UserResponse(
                    email = "leo@soongsil.ac.kr",
                    nickName = "nickname",
                    profileImage = ProfileImageUrlsResponse(
                        smallUrl = "smallUrl",
                        midUrl = "profileUrl",
                        largeUrl = "largeUrl"
                    )
                ))
        val refreshTokenCommand = RefreshTokenRequest("refresh.Token.refresh")

        val response = authService.issueRefreshToken(refreshTokenCommand)

        assertEquals(1L, response.studentId)
    }
}