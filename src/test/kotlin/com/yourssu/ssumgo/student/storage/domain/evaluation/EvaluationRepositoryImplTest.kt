package com.yourssu.ssumgo.student.storage.domain.evaluation

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.CommentFixture.COMMENT
import com.yourssu.ssumgo.common.support.fixture.EvaluationFixture
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_LEO
import com.yourssu.ssumgo.common.support.fixture.StudentFixture.STUDENT_MAI
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture.SUBJECT_1
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.comment.CommentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.posts.PostsRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

@ApplicationTest
class EvaluationRepositoryImplTest {
    @Autowired
    lateinit var evaluationRepositoryImpl: EvaluationRepositoryImpl

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
    private var comment: Comment? = null

    @BeforeEach
    fun setUp() {
        mentee = studentRepository.saveOrUpdate(STUDENT_LEO.toDomain())
        mentor = studentRepository.saveOrUpdate(STUDENT_MAI.toDomain())
        subject = subjectRepository.save(SUBJECT_1.toDomain())
        posts = postsRepository.save(PostFixture.POST.toDomain(mentee!!, subject!!))
        comment = commentRepository.save(COMMENT.toDomain(mentor!!, posts!!))
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class save_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 답변과_멘토와_평가정보가_주어지면 {
            @Test
            @DisplayName("평가 도메인을 저장한다.")
            fun success() {
                val evaluation = EvaluationFixture.FIVE.toDomain(mentee!!, comment!!)

                val actual = evaluationRepositoryImpl.save(evaluation)

                assertNotNull(actual)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class getByCommentId_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 답변에_해당하는_평가가_존재하지_않으면 {
            @Test
            @DisplayName("EvaluationNotFoundException 예외를 던진다.")
            fun success() {
                assertThrows<EvaluationNotFoundException>(
                    { evaluationRepositoryImpl.getByCommentId(1L) },
                )
            }
        }
    }
}