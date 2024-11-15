package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.common.business.domain.auth.AuthService
import com.yourssu.ssumgo.common.business.domain.auth.SignInCommand
import com.yourssu.ssumgo.common.business.domain.auth.SignInResponse
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
import org.springframework.test.context.bean.override.mockito.MockitoBean
import kotlin.test.assertEquals

@SpringBootTest
class StudentServiceTest {
    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var authService: AuthService

    @MockitoBean
    private lateinit var authClient: AuthFeignClientImpl

    @MockitoBean
    private lateinit var userClient: SoomsilFeignClientImpl

    @Test
    fun getStudent() {
        val signInResponse = signIn(email = "leo@soonsil.ac.kr", password = "password")

        val response = studentService.getStudent(signInResponse.studentId)

        assertEquals(signInResponse.studentId, response.studentId)
    }

    private fun signIn(email: String, password: String): SignInResponse {
        val signInCommand = SignInCommand(email, password)
        `when`(authClient.signIn(any()))
            .thenReturn(TokenResponse("access.Token.sign", "refresh.Token.sign"))
        `when`(userClient.getUser(any()))
            .thenReturn(
                UserResponse(
                    email = signInCommand.email,
                    nickName = signInCommand.email.replace("@soongsil.ac.kr", ""),
                    profileImage = ProfileImageUrlsResponse(
                        smallUrl = "smallUrl",
                        midUrl = "profileUrl",
                        largeUrl = "largeUrl"
                    )
                )
            )
        return authService.signIn(signInCommand)
    }
}