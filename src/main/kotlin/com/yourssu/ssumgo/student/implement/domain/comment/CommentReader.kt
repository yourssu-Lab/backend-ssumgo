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
    fun getAllByMentee(subjectId: Long, menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return commentRepository.findAllByMentee(
            subjectId = subjectId,
            menteeId = menteeId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }

    @Transactional(readOnly = true)
    fun getAllByMentee2(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return commentRepository.findAllByMentee2(
            menteeId = menteeId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }

    @Transactional(readOnly = true)
    fun getAllByNotMentee(subjectId: Long, menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return commentRepository.findAllByNotMentee(
            subjectId = subjectId,
            menteeId = menteeId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }
}