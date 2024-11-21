package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.evaluation.Evaluation
import com.yourssu.ssumgo.student.implement.domain.evaluation.Rating
import com.yourssu.ssumgo.student.implement.domain.student.Student

enum class EvaluationFixture(
    val rating: Rating,
    val additionalInfo: String,
) {
    ONE(Rating.ONE, "매우 나쁨"),
    TWO(Rating.TWO, "나쁨"),
    THREE(Rating.THREE, "보통"),
    FOUR(Rating.FOUR, "좋음"),
    FIVE(Rating.FIVE, "매우 좋음");

    fun toDomain(mentee: Student, comment: Comment): Evaluation {
        return Evaluation(
            mentee = mentee,
            comment = comment,
            rating = rating,
            additionalInfo = additionalInfo,
        )
    }
}