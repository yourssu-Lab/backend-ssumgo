package com.yourssu.ssumgo.student.business.domain.evaluation

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.common.support.fixture.EvaluationFixture.FIVE
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.evaluation.EvaluationAlreadyExistsException
import com.yourssu.ssumgo.student.implement.domain.evaluation.EvaluationWriter
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ApplicationTest
class EvaluationServiceTest {
    @Autowired
    private lateinit var evaluationService: EvaluationService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var postsWriter: PostsWriter

    @Autowired
    private lateinit var commentWriter: CommentWriter

    @Autowired
    private lateinit var evaluationWriter: EvaluationWriter

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class saveEvaluation_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록되지_않은_평가의_답변과_멘티이면 {
            @Test
            @DisplayName("평가를 등록한다.")
            fun success() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
                val comment = commentWriter.save(COMMENT.toDomain(mentee, posts))
                val command = FIVE.toEvaluationCreatedCommand(mentee.id!!, comment.id!!)

                val response = evaluationService.saveEvaluation(command)

                assertNotNull(response)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_평가의_답변이면 {
            var mentee: Student? = null
            var comment: Comment? = null

            @BeforeEach
            fun setUp() {
                mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val posts = postsWriter.save(PostFixture.POST.toDomain(mentee!!, subject))
                comment = commentWriter.save(COMMENT.toDomain(mentee!!, posts))
                evaluationWriter.save(FIVE.toDomain(mentee!!, comment!!))
            }

            @Test
            @DisplayName("EvaluationAlreadyExistsException 예외를 던진다.")
            fun success() {
                val command = FIVE.toEvaluationCreatedCommand(mentee!!.id!!, comment!!.id!!)


                assertThrows<EvaluationAlreadyExistsException> {
                    evaluationService.saveEvaluation(command) }
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getByComment_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 답변에_해당하는_평가가_존재하면 {
            var comment: Comment? = null

            @BeforeEach
            fun setUp() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val posts = postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
                comment = commentWriter.save(COMMENT.toDomain(mentee, posts))
                evaluationWriter.save(FIVE.toDomain(mentee, comment!!))
            }

            @Test
            @DisplayName("해당_평가를_반환한다.")
            fun success() {
                val response = evaluationService.getByComment(comment!!.id!!)

                assertEquals(comment!!.id, response.commentId)
            }
        }
    }
}