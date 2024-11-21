package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.common.support.config.ApplicationTest
import com.yourssu.ssumgo.common.support.fixture.PageFixture
import com.yourssu.ssumgo.common.support.fixture.PostFixture
import com.yourssu.ssumgo.common.support.fixture.StudentFixture
import com.yourssu.ssumgo.common.support.fixture.SubjectFixture
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ApplicationTest
class PostsServiceTest {
    @Autowired
    private lateinit var postsService: PostsService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Autowired
    private lateinit var postsWriter: PostsWriter

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class savePosts_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class 등록된_과목과_멘티이면 {
            @Test
            @DisplayName("질문을 등록한다.")
            fun success() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                val command = PostFixture.POST.toPostsCreatedCommand(mentee.id!!, subject.id!!)

                val response = postsService.savePosts(command)

                assertNotNull(response)
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class getPostsById_메서드는 {
            @BeforeEach
            fun setUp() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                val subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                postsWriter.save(PostFixture.POST.toDomain(mentee, subject))
            }

            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
            inner class 등록된_질문이면 {
                @Test
                @DisplayName("질문_도메인을_반환한다.")
                fun success() {
                    val response = postsService.getPostsById(1L)

                    assertNotNull(response)
                }
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
        inner class findAllPostsBySubjectId_메서드는 {
            private var subject: Subject? = null

            @BeforeEach
            fun setUp() {
                val mentee = studentWriter.save(StudentFixture.STUDENT_LEO.toDomain())
                subject = subjectWriter.save(SubjectFixture.SUBJECT_1.toDomain())
                postsWriter.save(PostFixture.POST.toDomain(mentee, subject!!))
            }

            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
            inner class 과목에_해당하는_질문이_있으면 {
                @Test
                @DisplayName("해당하는_모든_질문을_반환한다.")
                fun success() {
                    val response = postsService.findAllPostsBySubjectId(
                        PageFixture.PAGE_LATEST.toPostsFoundBySubjectCommand(subject!!.id!!)
                    )

                    assertEquals(1, response.totalCount)
                }
            }
        }
    }
}