package com.yourssu.ssumgo.common.storage.domain.auth

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun generateTokenWithId() {
        val id = 1L

        val token: String = jwtTokenProvider.generateTokenWithId(id)

        assertNotNull(token)
    }
}