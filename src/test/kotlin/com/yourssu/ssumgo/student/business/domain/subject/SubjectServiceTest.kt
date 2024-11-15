package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.Test

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubjectServiceTest {

    @Autowired
    private lateinit var subjectService: SubjectService

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Test
    fun getAllSubjects() {
        val subject = Subject(
                subjectName = "과목명",
                professorName = "교수명",
                completion = "이수구분",
                subjectCode = 1234,
                time = 1,
                credit = 3
            )
        subjectWriter.save(subject)

        val subjects = subjectService.getAllSubjects()

        assertEquals(1, subjects.size)
    }

    @Test
    fun getSubjectsByStudent() {
        //given
        val student = Student(
            yourssuId = "yourssuId",
            nickname = "nickname",
            profileImageUrls = ProfileImageUrls(
                smallUrl = "smallUrl",
                midUrl = "mediumUrl",
                largeUrl = "largeUrl",
            )
        )
        val savedStudent = studentWriter.signIn(student)

        val subject = Subject(
                subjectName = "과목명",
                professorName = "교수명",
                completion = "이수구분",
                subjectCode = 1234,
                time = 1,
                credit = 3
            )
        val savedSubject = subjectWriter.save(subject)

        subjectWriter.saveStudentSubject(savedStudent, savedSubject, 2024, 2)

        //when
        val subjects = subjectService.getSubjectsByStudent(savedStudent.id!!, 2024, 2)

        //then
        assertEquals(1, subjects.size)
    }
}