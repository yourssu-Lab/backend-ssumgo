package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts

data class PostsResponse(
    val postId: Long,
    val menteeId: Long,
    val subjectId: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun from(posts: Posts): PostsResponse {
            return PostsResponse(
                postId = posts.id!!,
                menteeId = posts.mentee.id!!,
                subjectId = posts.subject.id!!,
                title = posts.title,
                content = posts.content
            )
        }
    }
}