package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.assertEquals

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubjectRepositoryImplTest {

    @Autowired
    private lateinit var subjectRepositoryImpl: SubjectRepositoryImpl

    private val subjectFixture = Subject(
        subjectName = "과목명",
        professorName = "교수명",
        completion = "이수구분",
        subjectCode = 1234,
        time = 1,
        credit = 3,
    )

    @Test
    fun save() {
        val response = subjectRepositoryImpl.save(subjectFixture)

        assertEquals(subjectFixture.subjectCode, response.subjectCode)
    }

    @Test
    fun getAllSubjects() {
        subjectRepositoryImpl.save(subjectFixture)

        val response = subjectRepositoryImpl.getAllSubjects()

        assertEquals(1, response.size)
    }

    @Test
    fun get() {
        val savedSubject = subjectRepositoryImpl.save(subjectFixture)

        val response = subjectRepositoryImpl.get(savedSubject.id!!)

        assertEquals(savedSubject.id, response.id)
    }
}