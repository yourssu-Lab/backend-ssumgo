package com.yourssu.ssumgo.student.business.domain.star

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.common.support.fixture.PageFixture.PAGE_LATEST
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.star.Star
import com.yourssu.ssumgo.student.implement.domain.star.StarWriter
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ApplicationTest
class StarServiceTest {
    @Autowired
    private lateinit var starService: StarService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var postsWriter: PostsWriter

    @Autowired
    private lateinit var commentWriter: CommentWriter

    @Autowired
    private lateinit var starWriter: StarWriter

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class saveStar_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_학생과_답변_이면 {
            @Test
            @DisplayName("보관함에 등록한다.")
            fun success() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val mentor = studentWriter.save(StudentFixture.STUDENT_MAI.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
                val comment = commentWriter.save(COMMENT.toDomain(mentor, posts))
                val command = StarCreatedCommand(
                    commentId = comment.id!!,
                    studentId = mentee.id!!,
                )

                val response = starService.saveStar(command)

                assertNotNull(response)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getAllByStudent_메서드는 {
        var student: Student? = null

        @BeforeEach
        fun setUp() {
            student = studentWriter.save(StudentFixture.STUDENT_MAI.toDomain())
            val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
            val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
            val comment = commentWriter.save(COMMENT.toDomain(mentee, posts))
            starWriter.save(Star(comment = comment, student = student!!))
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 학생이_등록한_좋아요가_존재하면 {
            @Test
            @DisplayName("보관함에 있는 답변을 모두 반환한다.")
            fun success() {
                val commentsPage = starService.getAllByStudent(
                    student!!.id!!,
                    PAGE_LATEST.page,
                    PAGE_LATEST.size,
                    PAGE_LATEST.sortBy
                )

                assertEquals(1, commentsPage.totalElements)
            }
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getAllCommentByStar_메서드는 {
        @BeforeEach
        fun setUp() {
            val student = studentWriter.save(StudentFixture.STUDENT_MAI.toDomain())
            val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
            val posts1 = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
            val posts2 = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
            val comment = commentWriter.save(COMMENT.toDomain(mentee, posts1))
            commentWriter.save(COMMENT.toDomain(mentee, posts2))
            starWriter.save(Star(comment = comment, student = student))
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 좋아요한_답변이_존재하면 {
            @Test
            @DisplayName("좋아요를 많이 받은 순으로 답변을 반환한다.")
            fun success() {
                val comments = starService.getAllCommentByStar()

                assertEquals(2, comments.size)
            }
        }
    }
}