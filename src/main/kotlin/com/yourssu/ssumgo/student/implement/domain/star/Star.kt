package com.yourssu.ssumgo.student.implement.domain.star

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.student.Student

class Star(
    var id: Long? = null,
    val student: Student,
    val comment: Comment,
) {
}