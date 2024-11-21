package com.yourssu.ssumgo.student.implement.domain.evaluation

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EvaluationReader(
    private val evaluationRepository: EvaluationRepository
) {
    @Transactional(readOnly = true)
    fun get(commentId: Long): Evaluation {
        return evaluationRepository.getByCommentId(commentId)
    }
}