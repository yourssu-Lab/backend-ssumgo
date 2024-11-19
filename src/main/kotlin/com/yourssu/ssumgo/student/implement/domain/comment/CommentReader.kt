package com.yourssu.ssumgo.student.implement.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentReader(
    private val commentRepository: CommentRepository
) {
    @Transactional(readOnly = true)
    fun getById(postId: Long, commentId: Long): Comment {
        return commentRepository.get(postId = postId, commentId = commentId)
    }

    @Transactional(readOnly = true)
    fun getAllBySubject(subjectId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return commentRepository.findAllBySubject(
            subjectId = subjectId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }

    @Transactional(readOnly = true)
    fun getAllByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return commentRepository.findAllByMentee(
            menteeId = menteeId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }
}