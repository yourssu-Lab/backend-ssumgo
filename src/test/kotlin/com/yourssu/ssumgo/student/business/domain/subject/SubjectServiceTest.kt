package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ApplicationTest
class SubjectServiceTest {
    @Autowired
    private lateinit var subjectService: SubjectService

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class saveSubject_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 과목_생성_요청_커멘드가_주어지면 {
            @Test
            @DisplayName("과목을_생성한다.")
            fun success() {
                val response = subjectService.saveSubject(SUBJECT_1.toCreatedCommand())

                assertNotNull(response)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getAllSubjects_메서드는 {
        @BeforeEach
        fun setUp() {
            subjectService.saveSubject(SUBJECT_1.toCreatedCommand())
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 과목이_존재하면 {
            @Test
            @DisplayName("존재하는_모든 과목을 반환한다.")
            fun success() {
                val response = subjectService.getAllSubjects()

                assertEquals(1, response.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getSubjectsByStudent_메서드는 {
        private var student: Student? = null

        @BeforeEach
        fun setUp() {
            student = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            val subject = subjectWriter.save(SUBJECT_1.toDomain())
            subjectWriter.saveStudentSubject(student!!, subject, 2024, 2)
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 학생_정보가_주어지면 {
            @Test
            @DisplayName("학생이 수강하는 모든 과목을 반환한다.")
            fun success() {
                val response = subjectService.getSubjectsByStudent(student!!.id!!, 2024, 2)

                assertEquals(1, response.size)
            }
        }
    }
}