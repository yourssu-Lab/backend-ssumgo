package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.*
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired

@ApplicationTest
class StudentServiceTest {
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
    inner class getStudent_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 학생_아이디를_받으면 {
            @Test
            @DisplayName("학생_정보를_반환한다.")
            fun success() {
                val student = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())

                val response = studentService.getStudent(student.id!!)

                assertEquals(student.yourssuId, response.yourssuId)
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllCommentsByMentee_메서드는 {
        private var mentee: Student? = null

        @BeforeEach
        fun setUp() {
            mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            val mentor = studentWriter.save(StudentFixture.STUDENT_TOM.toDomain())
            val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
            val posts = postsWriter.save(PostFixture.POST.toDomain(mentee!!, subject))
            commentWriter.save(CommentFixture.COMMENT.toDomain(mentor, posts))
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 멘토가_등록한_답변이_있으면 {
            @Test
            @DisplayName("멘티가 등록한 답변을 모두 반환한다.")
            fun success() {
                val response =
                    studentService.findAllCommentsByMentee(PageFixture.PAGE_LATEST.toCommentFoundByMenteeCommand(mentee!!.id!!))

                assertEquals(1, response.totalCount)
            }
        }
    }


    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class findAllPostsByMentee_메서드는 {
        private var mentee: Student? = null

        @BeforeEach
        fun setUp() {
            mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
            val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
            postsWriter.save(PostFixture.POST.toDomain(mentee!!, subject))
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 멘티가_등록한_질문이_있으면 {
            @Test
            @DisplayName("멘티가 등록한 질문을 모두 반환한다.")
            fun success() {
                val response =
                    studentService.findAllPostsByMentee(
                        PageFixture.PAGE_LATEST.toPostsFoundByMenteeCommand(
                            mentee!!.id!!
                        )
                    )

                assertEquals(1, response.totalCount)
            }
        }
    }
}
