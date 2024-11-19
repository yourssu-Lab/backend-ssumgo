package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentWriter
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.Test

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentServiceTest {
    @Autowired
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var studentWriter: StudentWriter

    @Autowired
    private lateinit var postsWriter: PostsWriter

    @Autowired
    private lateinit var subjectWriter: SubjectWriter

    @Test
    fun save() {
        val mentee = saveStudent("mentee")
        val mentor = saveStudent("mentor")
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)
        val command = CommentCreatedCommand(
            mentorId = mentor.id!!,
            postsId = posts.id!!,
            title = "title",
            content = "content"
        )

        //when
        val response = commentService.saveComment(command)

        //then
        assertNotNull(response)
    }

    @Test
    fun getCommentById() {
        val mentee = saveStudent("mentee")
        val mentor = saveStudent("mentor")
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)
        val comment = saveComment(mentor, posts)

        //when
        val response = commentService.getCommentById(postId = posts.id!!, comment.commentId)

        //then
        assertEquals(comment.commentId, response.commentId)
    }

    @Test
    fun findAllCommentsBySubject() {
        val mentee = saveStudent("mentee")
        val mentor = saveStudent("mentor")
        val subject = saveSubject()
        val posts1 = savePosts(mentee, subject)
        val posts2 = savePosts(mentee, subject)
        val comment1 = saveComment(mentor, posts1)
        val comment2 = saveComment(mentor, posts2)
        val command = CommentFoundBySubjectCommand(
            subjectId = subject.id!!,
            page = 1,
            sortBy = SortBy.LATEST,
            size = 10,
        )

        //when
        val response = commentService.findAllCommentsByMentee(command)

        //then
        assertEquals(2, response.totalCount)
    }

    private fun saveComment(mentor: Student, posts: Posts): CommentResponse {
        val command = CommentCreatedCommand(
            mentorId = mentor.id!!,
            postsId = posts.id!!,
            title = "title",
            content = "content"
        )
        return commentService.saveComment(command)
    }

    private fun saveStudent(yourssuId: String = "yourssuId"): Student {
        return studentWriter.signIn(
            Student(
                yourssuId = yourssuId,
                nickname = "nickname",
                profileImageUrls = ProfileImageUrls(
                    smallUrl = "smallUrl",
                    midUrl = "mediumUrl",
                    largeUrl = "largeUrl",
                )
            )
        )
    }

    private fun saveSubject(subjectCode: Int = 1234): Subject {
        return subjectWriter.save(
            Subject(
                subjectName = "과목명",
                professorName = "교수명",
                completion = "이수구분",
                subjectCode = subjectCode,
                time = 1,
                credit = 3,
            )
        )
    }

    private fun savePosts(mentee: Student, subject: Subject): Posts {
        val post = Posts(
            title = "title",
            content = "content",
            mentee = mentee,
            subject = subject,
        )
        return postsWriter.savePost(post)
    }
}