package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student

data class CommentCreatedCommand(
    val mentorId: Long,
    val postsId: Long,
    val title: String,
    val content: String,
) {
    fun toDomain(mentor: Student, posts: Posts): Comment {
        return Comment(
            mentor = mentor,
            posts = posts,
            title = title,
            content = content
        )
    }
}
