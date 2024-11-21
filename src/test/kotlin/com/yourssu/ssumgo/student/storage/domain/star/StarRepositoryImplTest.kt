package com.yourssu.ssumgo.student.storage.domain.star

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.*
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.star.Star
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.comment.CommentNotFoundException
import com.yourssu.ssumgo.student.storage.domain.comment.CommentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.posts.PostsRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

@ApplicationTest
class StarRepositoryImplTest {
    @Autowired
    private lateinit var starRepository: StarRepositoryImpl

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
    private var student: Student? = null
    private var subject: Subject? = null
    private var posts: Posts? = null
    private var posts2: Posts? = null
    private var comment: Comment? = null

    @BeforeEach
    fun setUp() {
        mentee = studentRepository.saveOrUpdate(STUDENT_LEO.toDomain())
        mentor = studentRepository.saveOrUpdate(STUDENT_MAI.toDomain())
        student = studentRepository.saveOrUpdate(STUDENT_TOM.toDomain())
        subject = subjectRepository.save(SUBJECT_1.toDomain())
        posts = postsRepository.save(PostFixture.POST.toDomain(mentee!!, subject!!))
        posts2 = postsRepository.save(PostFixture.POST.toDomain(mentee!!, subject!!))
        comment = commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 질문과_학생_도메인이_주어지면 {
            @Test
            @DisplayName("좋아요 도메인을 저장한다.")
            fun success() {
                val star = Star(
                    student = student!!,
                    comment = comment!!
                )

                val actual = starRepository.save(star)

                assertNotNull(actual)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getAllByStudentId_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 해당_학생이_보관함에_등록한_답변이_있을_경우 {
            @BeforeEach
            fun setUp() {
                val star = Star(
                    student = student!!,
                    comment = comment!!
                )
                starRepository.save(star)
            }

            @Test
            @DisplayName("해당 학생이 보관한 모든 답변들을 반환한다.")
            fun success() {
                val actual = starRepository.getAllByStudentId(
                    studentId = student!!.id!!,
                    pageNumber = 0,
                    pageSize = 10,
                    sortBy = SortBy.LATEST
                )

                assertEquals(1, actual.content.size)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class deleteByCommentId_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 학생이_등록한_좋아요와_답변에_해당하는_좋아요가_있으면 {
            @Test
            @DisplayName("보관함에서 삭제한다.")
            fun success() {
                val star = Star(
                    student = student!!,
                    comment = comment!!
                )
                val savedStar = starRepository.save(star)

                starRepository.deleteByStudentIdAndCommentId(student!!.id!!, comment!!.id!!)

                assertThrows<StarNotFoundException> {
                    starRepository.get(savedStar.id!!)
                }
            }
        }
    }
}