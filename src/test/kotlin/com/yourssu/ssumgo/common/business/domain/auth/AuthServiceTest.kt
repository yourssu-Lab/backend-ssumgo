package com.yourssu.ssumgo.common.business.domain.auth

import com.yourssu.ssumgo.common.application.domain.auth.RefreshTokenRequest
import com.yourssu.ssumgo.common.implement.support.soomsil.ProfileImageUrlsResponse
import com.yourssu.ssumgo.common.implement.support.soomsil.TokenResponse
import com.yourssu.ssumgo.common.implement.support.soomsil.UserResponse
import com.yourssu.ssumgo.common.storage.support.soomsil.AuthFeignClientImpl
import com.yourssu.ssumgo.common.storage.support.soomsil.SoomsilFeignClientImpl
import com.yourssu.ssumgo.common.support.config.ApplicationTest
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.bean.override.mockito.MockitoBean
import kotlin.test.assertNotNull

@ApplicationTest
class AuthServiceTest {
    @Autowired
    private lateinit var authService: AuthService

    @MockitoBean
    private lateinit var authClient: AuthFeignClientImpl

    @MockitoBean
    private lateinit var userClient: SoomsilFeignClientImpl

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class signIn_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 가입되어_있는_유어슈_유저이면 {
            @Test
            @DisplayName("엑세스_토큰과_리프레시_토큰을_반환한다.")
            fun success() {
                val response = signIn()

                assertNotNull(response)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class issueRefreshToken_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 올바른_리프레시_토큰이면 {
            @Test
            @DisplayName("엑세스_토큰과_리프레시_토큰을_반환한다.")
            fun success() {
                `when`(authClient.getRefreshToken(any()))
                    .thenReturn(TokenResponse("access.Token.sign", "refresh.Token.sign"))
                val response = authService.issueRefreshToken(RefreshTokenRequest(signIn().refreshToken))

                assertNotNull(response)
            }
        }
    }

    private fun signIn(email: String = "leo@soongsil.ac.kr"): SignInResponse {
        `when`(authClient.signIn(any()))
            .thenReturn(TokenResponse("access.Token.sign", "refresh.Token.sign"))
        `when`(userClient.getUser(any()))
            .thenReturn(
                UserResponse(
                    email = email,
                    nickName = "nickname",
                    profileImage = ProfileImageUrlsResponse(
                        smallUrl = "smallUrl",
                        midUrl = "profileUrl",
                        largeUrl = "largeUrl"
                    )
                )
            )
        val signInCommand = SignInCommand(email, "password")
        return authService.signIn(signInCommand)
    }
}