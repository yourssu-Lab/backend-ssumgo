package com.yourssu.ssumgo.student.business.domain.posts

data class PostsPageResponse(
    val totalPages: Int,
    val totalCount: Int,
    val hasNext: Boolean,
    val postsList: List<PostsResponse>
) {
}
