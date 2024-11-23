package com.yourssu.ssumgo.student.implement.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage

interface CommentRepository {
    fun save(comment: Comment): Comment

    fun get(postId: Long, commentId: Long): Comment

    fun get(commentId: Long): Comment

    fun existsComment(postId: Long): Boolean

    fun findAllBySubject(
        subjectId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage
    fun findAllBySubjectWithSearch(subjectId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy, query: String): CommentsPage
    fun findAllByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage
}