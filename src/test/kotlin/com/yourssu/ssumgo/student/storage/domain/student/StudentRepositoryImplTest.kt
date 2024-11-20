package com.yourssu.ssumgo.student.storage.domain.student

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.student.implement.domain.student.Student
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

@ApplicationTest
class StudentRepositoryImplTest {
    @Autowired
    private lateinit var studentRepository: StudentRepositoryImpl

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class saveOrUpdate_메서드는 {
        private val student = StudentFixture.STUDENT_LEO.toStudent()
        private val editedStudent = StudentFixture.STUDENT_LEO.toStudent("editedNickname")

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 가입되지_않은_학생인_경우 {
            @Test
            @DisplayName("학생 도메인을 저장한다.")
            fun success() {
                val actual: Student = studentRepository.saveOrUpdate(student)

                assertEquals(student.yourssuId, actual.yourssuId)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 가입된_학생인_경우 {
            @BeforeEach
            fun setUp() {
                studentRepository.saveOrUpdate(student)
            }

            @Test
            @DisplayName("학생 정보를 업데이트한다.")
            fun success() {
                val actual: Student = studentRepository.saveOrUpdate(editedStudent)

                assertAll(
                    { assertEquals(student.yourssuId, actual.yourssuId) },
                    { assertEquals(editedStudent.nickname, actual.nickname) }
                )
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class get_메서드는 {
        val student = StudentFixture.STUDENT_LEO.toStudent()

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 가입된_학생인_경우 {
            @BeforeEach
            fun setUp(){
                studentRepository.saveOrUpdate(student)
            }

            @Test
            @DisplayName("학생 도메인을 반환한다.")
            fun success() {
                val actual: Student = studentRepository.get(1L)

                assertEquals(student.yourssuId, actual.yourssuId)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 가입되지_않은_학생인_경우 {
            @Test
            @DisplayName("StudentNotFoundException 예외를 던진다.")
            fun failure() {
                assertThrows<StudentNotFoundException> {
                    studentRepository.get(1L)
                }
            }
        }
    }

}
