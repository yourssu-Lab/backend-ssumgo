package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

@ApplicationTest
class SubjectRepositoryImplTest {
    @Autowired
    private lateinit var subjectRepositoryImpl: SubjectRepositoryImpl

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        val subject = SubjectFixture.SUBJECT_1.toDomain()

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록되지_않은_과목코드일_경우 {
            @Test
            @DisplayName("과목 도메인을 저장한다.")
            fun success() {
                val actual: Subject = subjectRepositoryImpl.save(subject)

                assertEquals(subject.subjectCode, actual.subjectCode)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 이미_등록된_과목코드일_경우 {
            @BeforeEach
            fun setUp() {
                subjectRepositoryImpl.save(subject)
            }

            @Test
            @DisplayName("SubjectAlreadyExistsException 예외를 던진다.")
            fun failure() {
                assertThrows<SubjectAlreadyExistsException>(
                    { subjectRepositoryImpl.save(subject) }
                )
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getAllSubjects_메서드는() {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_과목이_있는_경우 {
            @BeforeEach
            fun setUp() {
                subjectRepositoryImpl.save(SubjectFixture.SUBJECT_1.toDomain())
                subjectRepositoryImpl.save(SubjectFixture.SUBJECT_2.toDomain())
            }

            @Test
            @DisplayName("등록된 모든 과목을 반환한다.")
            fun success() {
                val actual: List<Subject> = subjectRepositoryImpl.getAllSubjects()

                assertEquals(2, actual.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class get_메서드는 {
        val subject = SubjectFixture.SUBJECT_1.toDomain()

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 존재하는_과목일_경우 {
            @BeforeEach
            fun setUp() {
                subjectRepositoryImpl.save(subject)
            }

            @Test
            @DisplayName("과목 도메인을 반환한다.")
            fun success() {
                val actual: Subject = subjectRepositoryImpl.get(1L)

                assertEquals(subject.subjectCode, actual.subjectCode)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 존재하지_않는_과목일_경우 {
            @Test
            @DisplayName("SubjectNotFoundException 예외를 던진다.")
            fun failure() {
                assertThrows<SubjectNotFoundException>(
                    { subjectRepositoryImpl.get(1L) }
                )
            }
        }
    }
}