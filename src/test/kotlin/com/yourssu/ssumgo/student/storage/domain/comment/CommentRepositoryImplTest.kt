package com.yourssu.ssumgo.student.storage.domain.comment

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.posts.PostsRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentRepositoryImplTest {
    @Autowired
    private lateinit var commentRepository: CommentRepositoryImpl

    @Autowired
    private lateinit var postsRepository: PostsRepositoryImpl

    @Autowired
    private lateinit var studentRepository: StudentRepositoryImpl

    @Autowired
    private lateinit var subjectRepository: SubjectRepositoryImpl

    @Test
    fun save() {
        // given
        val mentee = saveStudent()
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)

        val comment = Comment(
            mentor = mentee,
            posts = posts,
            title = "title",
            content = "content"
        )
        // when
        val response = commentRepository.save(comment)

        // then
        assertNotNull(response.id)
    }

    @Test
    @Transactional
    fun get() {
        // given
        val mentee = saveStudent()
        val mentor = saveStudent("mentor")
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)
        val comment = saveComment(mentor, posts)

        // when
        val response = commentRepository.get(postId = posts.id!!, commentId = comment.id!!)

        // then
        assertEquals(comment.id, response.id)
    }

    @Test
    @Transactional
    fun findAllByMentee() {
        // given
        val mentee = saveStudent()
        val mentor = saveStudent("mentor")
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)
        val comment1 = saveComment(mentor, posts)
        val comment2 = saveComment(mentor, posts)

        // when
        val response = commentRepository.findAllByMentee(
            subjectId = subject.id!!,
            menteeId = mentee.id!!,
            pageNumber = 0,
            pageSize = 10,
            sortBy = SortBy.LATEST
        )

        // then
        assertEquals(2, response.content.size)
    }

    @Test
    @Transactional
    fun findAllByNotMentee() {
        // given
        val mentee = saveStudent()
        val mentor = saveStudent("mentor")
        val otherMentee = saveStudent("otherMentee")
        val subject = saveSubject()
        val posts = savePosts(mentee, subject)
        val postsOther = savePosts(otherMentee, subject)
        val comment1 = saveComment(mentor, posts)
        val comment2 = saveComment(mentor, posts)
        val comment3 = saveComment(mentor, postsOther)

        // when
        val response = commentRepository.findAllByNotMentee(
            subjectId = subject.id!!,
            menteeId = mentee.id!!,
            pageNumber = 0,
            pageSize = 10,
            sortBy = SortBy.LATEST
        )

        // then
        assertEquals(1, response.content.size)
    }

    private fun saveComment(
        mentor: Student,
        posts: Posts,
        title: String = "title",
        content: String = "content"
    ): Comment {
        val comment = Comment(
            mentor = mentor,
            posts = posts,
            title = title,
            content = content
        )
        return commentRepository.save(comment)
    }

    private fun saveStudent(yourssuId: String = "leo"): Student {
        val profileImageUrlsFixture = ProfileImageUrls("small", "mid", "large")
        val student = Student(
            yourssuId = yourssuId,
            profileImageUrls = profileImageUrlsFixture
        )
        return studentRepository.saveOrUpdate(student)
    }

    private fun saveSubject(): Subject {
        val subject = Subject(
            subjectName = "과목명",
            professorName = "교수명",
            completion = "이수구분",
            subjectCode = 1234,
            time = 1,
            credit = 3,
        )
        return subjectRepository.save(subject)
    }

    private fun savePosts(
        mentee: Student,
        subject: Subject,
        title: String = "title",
        content: String = "content"
    ): Posts {
        val post = Posts(
            title = title,
            content = content,
            mentee = mentee,
            subject = subject,
        )
        return postsRepository.save(post)
    }
}