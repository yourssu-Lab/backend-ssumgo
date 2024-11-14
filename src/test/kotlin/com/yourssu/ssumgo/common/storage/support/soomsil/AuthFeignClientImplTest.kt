package com.yourssu.ssumgo.common.storage.support.soomsil

import com.yourssu.ssumgo.common.implement.support.soomsil.SignInClientRequest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthFeignClientImplTest {
    @Autowired
    private lateinit var authClient: AuthFeignClientImpl

    @Test
    @Disabled
    fun signIn() {
        val request = SignInClientRequest(email = "nggus5@soongsil.ac.kr", password = "leopassword123123")

        assertDoesNotThrow {
            val response = authClient.signIn(request)
        }
    }
}
