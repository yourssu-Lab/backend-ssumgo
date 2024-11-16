package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy

class PostsFoundBySubjectCommand(
    val subjectId: Long,
    val sortBy: SortBy,
    val page: Int,
    val size: Int
) {
}