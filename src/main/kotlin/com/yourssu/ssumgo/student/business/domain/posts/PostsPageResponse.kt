package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.storage.domain.posts.PostsPage

data class PostsPageResponse(
    val totalPages: Int,
    val totalCount: Int,
    val hasNext: Boolean,
    val postsList: List<PostsResponse>
) {
    companion object {
        fun from(postsPage: PostsPage): PostsPageResponse {
            return PostsPageResponse(
                totalPages = postsPage.totalPages,
                totalCount =  postsPage.totalElements.toInt(),
                hasNext = postsPage.hasNext,
                postsList = postsPage.content.map { PostsResponse.from(it) }
            )
        }
    }
}
