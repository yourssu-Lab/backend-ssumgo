package com.yourssu.ssumgo.student.implement.domain.evaluation

import com.yourssu.ssumgo.common.application.domain.common.ConflictException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EvaluationWriter(
    private val evaluationRepository: EvaluationRepository
) {
    @Transactional
    fun save(evaluation: Evaluation): Evaluation {
        if (evaluationRepository.existsByCommentId(evaluation.comment.id!!)) {
            throw EvaluationAlreadyExistsException()
        }
        return evaluationRepository.save(evaluation)
    }
}

class EvaluationAlreadyExistsException : ConflictException(message = "해당 답변에 이미 평가가 존재합니다.")
