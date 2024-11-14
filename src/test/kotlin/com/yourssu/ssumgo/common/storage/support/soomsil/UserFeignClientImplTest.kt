package com.yourssu.ssumgo.common.storage.support.soomsil

import com.yourssu.ssumgo.common.implement.domain.auth.AccessTokenGenerator
import com.yourssu.ssumgo.common.implement.support.soomsil.SignInClientRequest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserFeignClientImplTest {
    @Autowired
    private lateinit var authClient: AuthFeignClientImpl

    @Autowired
    private lateinit var userClient: SoomsilFeignClientImpl

    @Autowired
    private lateinit var accessTokenGenerator: AccessTokenGenerator

    @Test
    @Disabled
    fun getUser() {
        val request = SignInClientRequest(email = "nggus5@soongsil.ac.kr", password = "leopassword123123")
        val tokenResponse = authClient.signIn(request)

        assertDoesNotThrow {
            val response = userClient.getUser(accessTokenGenerator.formatingBearer(tokenResponse.accessToken))
        }
    }
}
