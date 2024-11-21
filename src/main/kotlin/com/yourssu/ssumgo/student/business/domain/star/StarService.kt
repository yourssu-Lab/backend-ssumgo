package com.yourssu.ssumgo.student.business.domain.star

import com.yourssu.ssumgo.student.business.domain.comment.CommentResponse
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.star.Star
import com.yourssu.ssumgo.student.implement.domain.star.StarReader
import com.yourssu.ssumgo.student.implement.domain.star.StarWriter
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StarService(
    private val starReader: StarReader,
    private val starWriter: StarWriter,
    private val studentReader: StudentReader,
    private val commentReader: CommentReader,
) {
    @Transactional
    fun saveStar(command: StarCreatedCommand): CommentResponse {
        val student = studentReader.get(command.studentId)
        val comment = commentReader.get(command.commentId)
        val star = starWriter.save(command.toDomain(student, comment))
        return CommentResponse.from(star.comment)
    }

    fun getAllByStudent(studentId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return starReader.getAllByStudent(studentId, pageNumber, pageSize, sortBy)
    }

    fun deleteByComment(studentId: Long, commentId: Long) {
        starWriter.deleteByComment(studentId = studentId, commentId = commentId)
    }
}

data class StarCreatedCommand(
    val commentId: Long,
    val studentId: Long,
) {
    fun toDomain(student: Student, comment: Comment): Star {
        return Star(
            comment = comment,
            student = student,
        )
    }
}
