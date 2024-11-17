package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage

data class CommentsPageResponse(
    val totalPage: Int,
    val totalCount: Int,
    val hasNext: Boolean,
    val commentsList: List<CommentResponse>,
) {
    companion object {
        fun from(commentsPage: CommentsPage): CommentsPageResponse {
            return CommentsPageResponse(
                totalPage = commentsPage.totalPages,
                totalCount =  commentsPage.totalElements.toInt(),
                hasNext = commentsPage.hasNext,
                commentsList = commentsPage.content.map { CommentResponse.from(it) }
            )
        }
    }

}
