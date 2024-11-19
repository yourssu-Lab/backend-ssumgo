package com.yourssu.ssumgo.student.implement.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage

interface CommentRepository {
    fun save(comment: Comment): Comment

    fun get(postId: Long, commentId: Long): Comment

    fun existsComment(posts: Posts): Boolean

    fun findAllBySubject(
        subjectId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage

    fun findAllByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage
}