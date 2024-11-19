package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentReader: StudentReader,
    private val commentReader: CommentReader,
) {
    fun getStudent(studentId: Long): StudentResponse {
        val student = studentReader.getStudent(studentId)
        return StudentResponse.from(student)
    }

    fun findAllCommentsByMentee(command: CommentFoundByMenteeCommand): CommentsPageResponse {
        val mentee = studentReader.getStudent(command.menteeId)
        val commentsPage = commentReader.getAllByMentee2(
            menteeId = mentee.id!!,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy,
        )
        return CommentsPageResponse.from(commentsPage)
    }
}

data class CommentFoundByMenteeCommand(
    val menteeId: Long,
    val page: Int,
    val sortBy: SortBy,
    val size: Int,
)