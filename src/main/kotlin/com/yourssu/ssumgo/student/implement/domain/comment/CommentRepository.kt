package com.yourssu.ssumgo.student.implement.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage

interface CommentRepository {
    fun save(comment: Comment): Comment

    fun get(postId: Long, commentId: Long): Comment

    fun findAllByMentee(
        subjectId: Long,
        menteeId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage

    fun findAllByNotMentee(
        subjectId: Long,
        menteeId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage
}