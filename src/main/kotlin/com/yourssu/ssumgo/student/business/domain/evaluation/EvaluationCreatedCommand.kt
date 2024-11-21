package com.yourssu.ssumgo.student.business.domain.evaluation

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.evaluation.Evaluation
import com.yourssu.ssumgo.student.implement.domain.evaluation.Rating
import com.yourssu.ssumgo.student.implement.domain.student.Student

data class EvaluationCreatedCommand(
    val menteeId: Long,
    val commentId: Long,
    val rating: Rating,
    val additionalInfo: String,
) {
    fun toDomain(mentee: Student, comment: Comment): Evaluation {
        return Evaluation(
            mentee = mentee,
            comment =  comment,
            rating = rating,
            additionalInfo = additionalInfo,
        )
    }
}
