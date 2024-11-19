package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostsServiceTest {
    @Autowired
    private lateinit var postsService: PostsService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    private val student = Student(
        yourssuId = "yourssuId",
        nickname = "nickname",
        profileImageUrls = ProfileImageUrls(
            smallUrl = "smallUrl",
            midUrl = "mediumUrl",
            largeUrl = "largeUrl",
        )
    )

    private val subject = Subject(
        subjectName = "과목명",
        professorName = "교수명",
        completion = "이수구분",
        subjectCode = 1234,
        time = 1,
        department = "학과",
        credit = 3,
    )

    @Test
    fun savePosts() {
        // given
        val savedStudent = studentWriter.signIn(student)
        val savedSubject = subjectWriter.save(subject)
        val posts = Posts(
            title = "My title",
            content = "My content",
            mentee = savedStudent,
            subject = savedSubject
        )
        val command = PostsCreatedCommand(
            title = posts.title,
            content = posts.content,
            menteeId = savedStudent.id!!,
            subjectId = savedSubject.id!!
        )

        // when
        val response = postsService.savePosts(command)

        // then
        assertNotNull(response)
    }

    @Test
    fun getPostsById() {
        // given
        val savedStudent = studentWriter.signIn(student)
        val savedSubject = subjectWriter.save(subject)
        val posts = save(savedStudent, savedSubject)

        // when
        val response = postsService.getPostsById(posts.postId)

        // then
        assertEquals(posts.postId, response.postId)
    }

    @Test
    fun findAllPostsBySubjectId() {
        // given
        val savedStudent = studentWriter.signIn(student)
        val savedSubject = subjectWriter.save(subject)
        val posts = (1..21).map { save(
            mentee = savedStudent,
            subject = savedSubject,
            title = "title$it",
            content = "content$it",
        ) }

        // when
        // pageNumber는 1부터 시작
        val response = postsService.findAllPostsBySubjectId(
            PostsFoundBySubjectCommand(
                subjectId = savedSubject.id!!,
                page = 3,
                sortBy = SortBy.LATEST,
                size = 10
            )
        )

        // then
        assertEquals(1, response.postsList.size)
    }

    private fun save(
        mentee: Student,
        subject: Subject,
        title: String = "title",
        content: String = "content"): PostsResponse {
        val command = PostsCreatedCommand(
            title = title,
            content = content,
            menteeId = mentee.id!!,
            subjectId = subject.id!!
        )
        return postsService.savePosts(command)
    }
}