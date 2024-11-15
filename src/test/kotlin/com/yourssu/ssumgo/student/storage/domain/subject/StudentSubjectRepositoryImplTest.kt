package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.Test

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentSubjectRepositoryImplTest {

    @Autowired
    private lateinit var studentSubjectRepositoryImpl: StudentSubjectRepositoryImpl

    @Autowired
    private lateinit var subjectRepositoryImpl: SubjectRepositoryImpl

    @Autowired
    private lateinit var studentRepositoryImpl: StudentRepositoryImpl

    @Test
    fun save() {
        val student = Student(
            yourssuId = "yourssuId",
            nickname = "nickname",
            profileImageUrls = ProfileImageUrls(
                smallUrl = "smallUrl",
                midUrl = "mediumUrl",
                largeUrl = "largeUrl",
            )
        )
        val subject = Subject(
            subjectName = "과목명",
            professorName = "교수명",
            completion = "이수구분",
            subjectCode = 1234,
            time = 1,
            credit = 3,
        )
        val years = 2024
        val semester = 2
        val savedStudent = studentRepositoryImpl.saveOrUpdate(student)
        val savedSubject = subjectRepositoryImpl.save(subject)

        val response = studentSubjectRepositoryImpl.save(savedStudent, savedSubject, years, semester)

        assertEquals(1, response)
    }
}