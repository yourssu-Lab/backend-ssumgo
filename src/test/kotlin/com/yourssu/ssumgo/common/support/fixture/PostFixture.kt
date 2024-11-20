package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject

enum class PostFixture(
    val title : String,
    val content : String,
) {
    POST(
        title = "title",
        content = "content",
    );

    fun toDomain(mentee: Student, subject: Subject): Posts {
        return Posts(
            mentee = mentee,
            subject = subject,
            title = title,
            content = content,
        )
    }
}