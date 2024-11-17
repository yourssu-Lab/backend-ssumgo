package com.yourssu.ssumgo.student.implement.domain.comment

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student

class Comment(
    val id: Long? = null,
    val mentor: Student,
    val posts: Posts,
    val title: String,
    val content: String,
    ) {
}