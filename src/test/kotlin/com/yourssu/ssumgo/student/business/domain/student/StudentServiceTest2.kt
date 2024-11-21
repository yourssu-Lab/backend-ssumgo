package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired

@ApplicationTest
class StudentServiceTest2 {
    @Autowired
    private lateinit var studentService: StudentService

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