package com.yourssu.ssumgo.student.storage.domain.posts

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_LEO
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

@ApplicationTest
class PostsRepositoryImplTest {
    @Autowired
    private lateinit var postsRepository: PostsRepositoryImpl

    @Autowired
    private lateinit var studentRepository: StudentRepositoryImpl

    @Autowired
    private lateinit var subjectRepository: SubjectRepositoryImpl

    private var student: Student? = null
    private var subject: Subject? = null

    @BeforeEach
    fun setUp() {
        student = studentRepository.saveOrUpdate(STUDENT_LEO.toStudent())
        subject = subjectRepository.save(SUBJECT_1.toDomain())
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 질문과_멘티와_과목_도메인이_주어지면 {
            @Test
            @DisplayName("질문 도메인을 저장한다.")
            fun success() {
                val post = PostFixture.POST.toDomain(mentee = student!!, subject = subject!!)

                assertNotNull(postsRepository.save(post))
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class get_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당하는_질문이_존재하지_않으면 {
            @Test
            @DisplayName("PostsNotFoundException을 던진다.")
            fun failure() {
                assertThrows<PostsNotFoundException> {
                    postsRepository.get(1L)
                }
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllBySubjectId_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_과목에_등록된_질문이_있을_경우 {
            @BeforeEach
            fun setUp() {
                val post = PostFixture.POST.toDomain(mentee = student!!, subject = subject!!)
                postsRepository.save(post)
            }

            @Test
            @DisplayName("해당 과목의 모든 질문들을 반환한다.")
            fun success() {
                val actual = postsRepository.findAllBySubjectId(subject!!.id!!, 0, 10, SortBy.LATEST)

                assertEquals(1, actual.content.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllByMenteeId_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_멘티가_등록한_질문이_있을_꼉우 {
            @BeforeEach
            fun setUp() {
                val post = PostFixture.POST.toDomain(mentee = student!!, subject = subject!!)
                postsRepository.save(post)
            }

            @Test
            @DisplayName("해당 멘티가 등록한 모든 질문들을 반환한다.")
            fun success() {
                val actual = postsRepository.findAllByMenteeId(student!!.id!!, 0, 10, SortBy.LATEST)

                assertEquals(1, actual.content.size)
            }
        }
    }
}