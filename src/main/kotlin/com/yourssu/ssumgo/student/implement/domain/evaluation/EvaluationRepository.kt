package com.yourssu.ssumgo.student.implement.domain.evaluation

interface EvaluationRepository {
    fun save(evaluation: Evaluation): Evaluation
    fun getByCommentId(commentId: Long): Evaluation
}