package com.yourssu.ssumgo.student.implement.domain.star

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage

interface StarRepository {
    fun save(star: Star): Star
    fun get(id: Long): Star
    fun getAllByStudentId(studentId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage
    fun deleteByStudentIdAndCommentId(studentId: Long, commentId: Long)
    fun getCommentsByStar() : List<Comment>
}