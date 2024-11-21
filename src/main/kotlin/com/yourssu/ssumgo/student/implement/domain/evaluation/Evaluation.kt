package com.yourssu.ssumgo.student.implement.domain.evaluation

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.student.Student

class Evaluation(
    var id: Long? = null,
    val mentee: Student,
    val comment: Comment,
    val rating: Rating,
    val additionalInfo: String,
    ) {
}