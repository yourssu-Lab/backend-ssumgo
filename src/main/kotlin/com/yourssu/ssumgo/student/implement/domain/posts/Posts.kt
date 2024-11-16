package com.yourssu.ssumgo.student.implement.domain.posts

import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject

class Posts(
    val id : Long? = null,
    val mentee: Student,
    val subject: Subject,
    val title : String,
    val content : String,
) {
}