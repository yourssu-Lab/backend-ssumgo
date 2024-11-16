package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject

class PostsCreatedCommand(
    val menteeId: Long,
    val subjectId: Long,
    val title: String,
    val content: String
) {
    fun toDomain(mentee: Student, subject: Subject): Posts {
        return Posts(
            mentee = mentee,
            subject = subject,
            title = title,
            content = content
        )
    }
}