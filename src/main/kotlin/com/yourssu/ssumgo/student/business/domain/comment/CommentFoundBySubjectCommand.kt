package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy

data class CommentFoundBySubjectCommand(
    val subjectId: Long,
    val page: Int,
    val sortBy: SortBy,
    val size: Int,
    val query: String = "",
)