package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.comment.CommentWriter
import com.yourssu.ssumgo.student.implement.domain.posts.PostsReader
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentReader: CommentReader,
    private val commentWriter: CommentWriter,
    private val postsReader: PostsReader,
    private val studentReader: StudentReader,
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
        val commentsPage = commentReader.getAllByMentee(
            subjectId = command.subjectId,
            menteeId = command.menteeId,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy
        )
        return CommentsPageResponse.from(commentsPage)
    }

    fun findAllCommentsByNotMentee(command: CommentFoundBySubjectCommand): CommentsPageResponse {
        val commentsPage = commentReader.getAllByNotMentee(
            subjectId = command.subjectId,
            menteeId = command.menteeId,
            pageNumber = command.page,
            pageSize = command.size,
            sortBy = command.sortBy
        )
        return CommentsPageResponse.from(commentsPage)
    }
}