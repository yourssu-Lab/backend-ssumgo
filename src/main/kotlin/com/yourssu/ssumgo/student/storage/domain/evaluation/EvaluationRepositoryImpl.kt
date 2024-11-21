package com.yourssu.ssumgo.student.storage.domain.evaluation

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.evaluation.Evaluation
import com.yourssu.ssumgo.student.implement.domain.evaluation.EvaluationRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class EvaluationRepositoryImpl(
    private val evaluationJpaRepository: EvaluationJpaRepository,
) : EvaluationRepository {
    override fun save(evaluation: Evaluation): Evaluation {
        return evaluationJpaRepository.save(EvaluationEntity.toEntity(evaluation)).toDomain()
    }

    override fun getByCommentId(commentId: Long): Evaluation {
        return evaluationJpaRepository.getByCommentId(commentId)?.toDomain()
            ?: throw EvaluationNotFoundException()
    }

    override fun existsByCommentId(commentId: Long): Boolean {
        return evaluationJpaRepository.existsByCommentId(commentId)
    }
}


interface EvaluationJpaRepository : JpaRepository<EvaluationEntity, Long> {
    @Query("select e from EvaluationEntity e where e.comment.id = :commentId")
    fun getByCommentId(commentId: Long): EvaluationEntity?

    @Query("select (count(e) > 0) from EvaluationEntity e where e.comment.id = :commentId")
    fun existsByCommentId(commentId: Long): Boolean
}

class EvaluationNotFoundException : NotFoundException(message = "해당하는 답변 평가가 없습니다.")

