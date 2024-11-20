package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.business.domain.posts.PostsPageResponse
import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.posts.PostsReader
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentReader: StudentReader,
    private val postsReader: PostsReader,
    private val commentReader: CommentReader,
) {
    fun getStudent(studentId: Long): StudentResponse {
        val student = studentReader.get(studentId)
        return StudentResponse.from(student)
    }

    fun findAllCommentsByMentee(command: CommentFoundByMenteeCommand): CommentsPageResponse {
        val mentee = studentReader.get(command.menteeId)
        val commentsPage = commentReader.getAllByMentee(
            menteeId = mentee.id!!,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy,
        )
        return CommentsPageResponse.from(commentsPage)
    }

    fun findAllPostsByMentee(command: PostsFoundByMenteeCommand): PostsPageResponse {
        val mentee = studentReader.get(command.menteeId)
        val postsPage = postsReader.findAllByMentee(
            menteeId = mentee.id!!,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy,
        )
        return PostsPageResponse.from(postsPage)
    }
}

data class CommentFoundByMenteeCommand(
    val menteeId: Long,
    val page: Int,
    val sortBy: SortBy,
    val size: Int,
)

data class PostsFoundByMenteeCommand(
    val menteeId: Long,
    val page: Int,
    val sortBy: SortBy,
    val size: Int,
)