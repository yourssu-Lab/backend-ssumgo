package com.yourssu.ssumgo.student.storage.domain.evaluation

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.evaluation.Evaluation
import com.yourssu.ssumgo.student.implement.domain.evaluation.Rating
import com.yourssu.ssumgo.student.storage.domain.comment.CommentEntity
import com.yourssu.ssumgo.student.storage.domain.student.StudentEntity
import jakarta.persistence.*

@Entity
@Table(name = "evaluation")
class EvaluationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    val mentee: StudentEntity,

    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: CommentEntity,

    @Enumerated
    val rating: Rating,

    @Column(nullable = false)
    val additionalInfo: String,
) : BaseEntity() {
    companion object {
        fun toEntity(evaluation: Evaluation): EvaluationEntity {
            return EvaluationEntity(
                mentee = StudentEntity.toEntity(evaluation.mentee),
                comment = CommentEntity.toEntity(evaluation.comment),
                rating = evaluation.rating,
                additionalInfo = evaluation.additionalInfo,
            )
        }
    }

    fun toDomain(): Evaluation {
        return Evaluation(
            id = id,
            mentee = mentee.toDomain(),
            comment = comment.toDomain(),
            rating = rating,
            additionalInfo = additionalInfo,
        )
    }
}