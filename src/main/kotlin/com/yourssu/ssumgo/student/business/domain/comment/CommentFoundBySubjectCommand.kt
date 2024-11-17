package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy

data class CommentFoundBySubjectCommand(
    val menteeId: Long,
    val subjectId: Long,
    val page: Int,
    val sortBy: SortBy,
    val size: Int,
)