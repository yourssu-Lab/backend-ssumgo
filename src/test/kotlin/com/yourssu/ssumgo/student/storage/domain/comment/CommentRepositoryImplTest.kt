package com.yourssu.ssumgo.student.storage.domain.comment

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_LEO
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_MAI
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.posts.PostsRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ApplicationTest
class CommentRepositoryImplTest {
    @Autowired
    private lateinit var commentRepository: CommentRepositoryImpl

    @Autowired
    private lateinit var postsRepository: PostsRepositoryImpl

    @Autowired
    private lateinit var studentRepository: StudentRepositoryImpl

    @Autowired
    private lateinit var subjectRepository: SubjectRepositoryImpl

    private var mentee: Student? = null
    private var mentor: Student? = null
    private var subject: Subject? = null
    private var posts: Posts? = null
    private var posts2: Posts? = null

    @BeforeEach
    fun setUp() {
        mentee = studentRepository.saveOrUpdate(STUDENT_LEO.toDomain())
        mentor = studentRepository.saveOrUpdate(STUDENT_MAI.toDomain())
        subject = subjectRepository.save(SUBJECT_1.toDomain())
        posts = postsRepository.save(PostFixture.POST.toDomain(mentee!!, subject!!))
        posts2 = postsRepository.save(PostFixture.POST.toDomain(mentee!!, subject!!))
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 질문과_멘토_도메인이_주어지면 {
            @Test
            @DisplayName("답변 도메인을 저장한다.")
            fun success() {
                val comment = COMMENT.toDomain(mentor!!, posts!!)

                assertNotNull(commentRepository.save(comment))
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class get_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 질문에_해당하는_답변이_존재하지_않으면 {
            @Test
            @DisplayName("CommentNotFoundException 예외를 던진다.")
            fun success() {
                assertThrows<CommentNotFoundException>(
                    { commentRepository.get(posts!!.id!!, 1L) },
                )
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class existsComment_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 질문에_해당하는_답변이_존재하면 {
            @Test
            @DisplayName("true를 반환한다.")
            fun success() {
                commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))

                val actual = commentRepository.existsComment(posts!!.id!!)

                assertTrue(actual)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllBySubject_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_과목에_등록된_답변이_있을_꼉우 {
            @BeforeEach
            fun setUp() {
                commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))
                commentRepository.save(COMMENT.toDomain(mentor!!, posts2!!))
            }

            @Test
            @DisplayName("해당 과목에 등록된 모든 답변들을 반환한다.")
            fun success() {
                val actual = commentRepository.findAllBySubject(
                    subjectId = subject!!.id!!,
                    pageNumber = 0,
                    pageSize = 10,
                    sortBy = SortBy.LATEST)

                assertEquals(2, actual.content.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllWithQuery_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_과목에_등록된_답변이_검색어와_일치하지_않을_경우 {
            @BeforeEach
            fun setUp() {
                commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))
                commentRepository.save(COMMENT.toDomain(mentor!!, posts2!!))
            }

            @Test
            @DisplayName("해당 과목에 등록된 모든 답변들을 반환하지 않는다.")
            fun success() {
                val actual = commentRepository.findAllBySubjectWithSearch(
                    subjectId = subject!!.id!!,
                    pageNumber = 0,
                    pageSize = 10,
                    sortBy = SortBy.LATEST,
                    query = "content"
                )

                assertEquals(2, actual.content.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllByMentee_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_멘티가_등록한_질문에_답변이_있을_경우 {
            @BeforeEach
            fun setUp() {
                commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))
                commentRepository.save(COMMENT.toDomain(mentor!!, posts2!!))
            }

            @Test
            @DisplayName("해당 멘티의 질문에 등록된 모든 답변들을 반환한다.")
            fun success() {
                val actual = commentRepository.findAllByMentee(
                    menteeId = mentee!!.id!!,
                    pageNumber = 0,
                    pageSize = 10,
                    sortBy = SortBy.LATEST)

                assertEquals(2, actual.content.size)
            }
        }
    }
}