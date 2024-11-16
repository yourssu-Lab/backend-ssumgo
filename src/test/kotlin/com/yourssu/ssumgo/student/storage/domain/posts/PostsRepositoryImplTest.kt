package com.yourssu.ssumgo.student.storage.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostsRepositoryImplTest {
    @Autowired
    private lateinit var postsRepository: PostsRepositoryImpl

    @Autowired
    private lateinit var studentRepository: StudentRepositoryImpl

    @Autowired
    private lateinit var subjectRepository: SubjectRepositoryImpl

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
        credit = 3,
    )

    @Test
    fun save() {
        val title = "My title"
        val post = savePosts(
            mentee = studentRepository.saveOrUpdate(student),
            subject = subjectRepository.save(subject),
            title = title
        )

        assertEquals(title, post.title)
    }

    @Test
    @Transactional
    fun get() {
        val post = savePosts(
            mentee = studentRepository.saveOrUpdate(student),
            subject = subjectRepository.save(subject),
        )

        val response = postsRepository.get(post.id!!)

        assertEquals(post.id, response.id)

    }

    @Test
    @Transactional
    fun findAllBySubjectId() {
        val savedStudent = studentRepository.saveOrUpdate(student)
        val savedSubject = subjectRepository.save(subject)
        val posts = (1..21).map { savePosts(
            mentee = savedStudent,
            subject = savedSubject,
            title = "title$it",
            content = "content$it",
        ) }

        val response = postsRepository.findAllBySubjectId(
            subjectId = savedSubject.id!!,
            pageNumber = 0,
            sortBy = SortBy.LATEST,
            pageSize = 10
        )

        assertEquals(10, response.content.size)
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