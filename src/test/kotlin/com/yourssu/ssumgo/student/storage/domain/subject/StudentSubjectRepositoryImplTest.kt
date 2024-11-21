package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_LEO
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ApplicationTest
class StudentSubjectRepositoryImplTest {
    @Autowired
    private lateinit var studentSubjectRepositoryImpl: StudentSubjectRepositoryImpl

    @Autowired
    private lateinit var studentRepositoryImpl: StudentRepositoryImpl

    @Autowired
    private lateinit var subjectRepositoryImpl: SubjectRepositoryImpl

    private var student: Student? = null
    private var subject: Subject? = null

    @BeforeEach
    fun setUp() {
        student = studentRepositoryImpl.saveOrUpdate(STUDENT_LEO.toDomain())
        subject = subjectRepositoryImpl.save(SUBJECT_1.toDomain())
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_학생과_과목_도메인이_주어지면 {
            @Test
            @DisplayName("수강 과목 도메인을 저장한다.")
            fun success() {
                val actual = studentSubjectRepositoryImpl.save(
                    student = student!!,
                    subject = subject!!,
                    years = 2024,
                    semester = 2,
                )

                assertEquals(subject!!.subjectCode, actual.subjectCode)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class existsStudentSubject_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 이미_등록된_수강과목이면 {
            private val years = 2024
            private val semester = 2

            @BeforeEach
            fun setUp() {
                studentSubjectRepositoryImpl.save(
                    student = student!!,
                    subject = subject!!,
                    years = years,
                    semester = semester,
                )
            }

            @Test
            @DisplayName("true를 반환한다.")
            fun success() {
                val actual = studentSubjectRepositoryImpl.existsStudentSubject(
                    studentId = student!!.id!!,
                    subjectId = subject!!.id!!,
                    years = years,
                    semester = semester,
                )

                assertTrue(actual)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getSubjects_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_학생과_수강년도와_학기가_주어지면 {
            private val years = 2024

            private val semester = 2

            @BeforeEach
            fun setUp() {
                studentSubjectRepositoryImpl.save(
                    student = student!!,
                    subject = subject!!,
                    years = years,
                    semester = semester,
                )
            }

            @Test
            @DisplayName("해당하는 수강 과목들을 모두 반환한다.")
            fun success() {
                val actual = studentSubjectRepositoryImpl.getSubjects(
                    studentId = student!!.id!!,
                    years = years,
                    semester = semester,
                )

                assertEquals(1, actual.size)
            }
        }
    }
}