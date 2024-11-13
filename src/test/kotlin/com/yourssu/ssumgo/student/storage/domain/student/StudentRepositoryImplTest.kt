package com.yourssu.ssumgo.student.storage.domain.student

import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class StudentRepositoryImplTest {
    @Autowired
    private lateinit var studentRepositoryImpl: StudentRepositoryImpl

    private val yourssuIdFixture = "leo"
    private val profileImageUrlsFixture = ProfileImageUrls("small", "mid", "large")

    @Test
    fun saveOrUpdate() {
        val given = Student(
            yourssuId = yourssuIdFixture, profileImageUrls =
            profileImageUrlsFixture
        )

        val actual: Student = studentRepositoryImpl.saveOrUpdate(given)

        assertEquals(expected = yourssuIdFixture, actual = actual.yourssuId)
    }

    @Test
    fun get() {
        val student = Student(
            yourssuId = yourssuIdFixture, profileImageUrls =
            profileImageUrlsFixture
        )
        val given = studentRepositoryImpl.saveOrUpdate(student)

        val actual = studentRepositoryImpl.get(given.id!!)

        assertEquals(expected = yourssuIdFixture, actual = actual.yourssuId)
    }
}
