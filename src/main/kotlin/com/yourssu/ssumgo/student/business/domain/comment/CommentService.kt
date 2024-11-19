package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.posts.PostsReader
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectReader
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentReader: CommentReader,
    private val commentWriter: CommentWriter,
    private val postsReader: PostsReader,
    private val studentReader: StudentReader,
    private val subjectReader: SubjectReader,
) {
    fun saveComment(command: CommentCreatedCommand): CommentResponse {
        val mentor = studentReader.getStudent(command.mentorId)
        val posts = postsReader.getById(command.postsId)
        return CommentResponse.from(commentWriter.save(command.toDomain(mentor, posts)))
    }

    fun getCommentById(postId: Long, commentId: Long): CommentResponse {
        return CommentResponse.from(commentReader.getById(postId = postId, commentId = commentId))
    }

    fun findAllCommentsByMentee(command: CommentFoundBySubjectCommand): CommentsPageResponse {
        val subject = subjectReader.getSubject(command.subjectId)
        val mentee = studentReader.getStudent(command.menteeId)
        val commentsPage = commentReader.getAllByMentee(
            subjectId = subject.id!!,
            menteeId = mentee.id!!,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy
        )
        return CommentsPageResponse.from(commentsPage)
    }

    fun findAllCommentsByNotMentee(command: CommentFoundBySubjectCommand): CommentsPageResponse {
        val subject = subjectReader.getSubject(command.subjectId)
        val mentee = studentReader.getStudent(command.menteeId)
        val commentsPage = commentReader.getAllByNotMentee(
            subjectId = subject.id!!,
            menteeId = mentee.id!!,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy
        )
        return CommentsPageResponse.from(commentsPage)
    }
}