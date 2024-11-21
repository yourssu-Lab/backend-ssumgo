package com.yourssu.ssumgo.student.business.domain.evaluation

import com.yourssu.ssumgo.student.implement.domain.comment.CommentReader
import com.yourssu.ssumgo.student.implement.domain.evaluation.Evaluation
import com.yourssu.ssumgo.student.implement.domain.evaluation.EvaluationReader
import com.yourssu.ssumgo.student.implement.domain.evaluation.EvaluationWriter
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import org.springframework.stereotype.Service

@Service
class EvaluationService(
    private val evaluationWriter: EvaluationWriter,
    private val evaluationReader: EvaluationReader,
    private val studentReader: StudentReader,
    private val commentReader: CommentReader,
) {
    fun saveEvaluation(command: EvaluationCreatedCommand): EvaluationResponse {
        val mentee = studentReader.get(command.menteeId)
        val comment =  commentReader.get(command.commentId)
        val evaluation = evaluationWriter.save(command.toDomain(mentee, comment))
        return EvaluationResponse.from(evaluation)
    }

    fun getByComment(commentId: Long): EvaluationResponse {
        val evaluation = evaluationReader.get(commentId)
        return EvaluationResponse.from(evaluation)
    }
}

data class EvaluationResponse(
    val id: Long,
    val menteeId: Long,
    val commentId: Long,
    val rating: Int,
    val additionalInfo: String,
) {
    companion object {
        fun from(evaluation: Evaluation): EvaluationResponse {
            return EvaluationResponse(
                id = evaluation.id!!,
                menteeId = evaluation.mentee.id!!,
                commentId = evaluation.comment.id!!,
                rating = evaluation.rating.score,
                additionalInfo = evaluation.additionalInfo,
            )
        }
    }
}
