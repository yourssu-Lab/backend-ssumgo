package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.*
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.student.business.domain.student.StudentService
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ApplicationTest
class CommentServiceTest {
    @Autowired
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var postsWriter: PostsWriter

    @Autowired
    private lateinit var commentWriter: CommentWriter

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class saveComment_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_멘토와_질문_이면 {
            @Test
            @DisplayName("댓글을 등록한다.")
            fun success() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
                val command = COMMENT.toCommentCreatedCommand(mentee.id!!, posts.id!!)

                val response = commentService.saveComment(command)

                assertNotNull(response)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getCommentById_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 답변과_질문에_해당하는_답변이_존재하면 {
            var posts: Posts? = null
            var comment: Comment? = null

            @BeforeEach
            fun setUp() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
                comment = commentWriter.save(COMMENT.toDomain(mentee, posts!!))
            }

            @Test
            @DisplayName("답변을_반환한다.")
            fun success() {
                val response = commentService.getCommentById(posts!!.id!!, comment!!.id!!)

                assertNotNull(response)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllCommentsBySubject_메서드는 {
        var subject: Subject? = null

        @BeforeEach
        fun setUp() {
            val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
            val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject!!))
            commentWriter.save(COMMENT.toDomain(mentee, posts))
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 과목에_해당하는_답변이_존재하면 {
            @Test
            @DisplayName("해당하는 답변들을 모두 반환한다.")
            fun success() {
                val request= PageFixture.PAGE_LATEST.toCommentFoundBySubjectCommand(subject!!.id!!)

                val response = commentService.findAllCommentsBySubject(request)

                assertEquals(1, response.totalCount)
            }
        }
    }
}