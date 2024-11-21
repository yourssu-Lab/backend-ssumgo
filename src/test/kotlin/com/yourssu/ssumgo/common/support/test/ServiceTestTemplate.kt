package com.yourssu.ssumgo.common.support.test

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import org.junit.jupiter.api.*

@ApplicationTest
class ServiceTestTemplate {

    @BeforeEach
    fun setUp() {

    }
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class _메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 이면 {
            @Test
            @DisplayName("반환한다.")
            fun success() {
            }
        }
    }
}