package com.yourssu.ssumgo.student.storage.domain.star

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.star.Star
import com.yourssu.ssumgo.student.storage.domain.comment.CommentEntity
import com.yourssu.ssumgo.student.storage.domain.student.StudentEntity
import jakarta.persistence.*

@Entity
@Table(name = "star")
class StarEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: StudentEntity,

    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: CommentEntity,
) : BaseEntity() {
    companion object {
        fun toEntity(star: Star): StarEntity {
            return StarEntity(
                student = StudentEntity.toEntity(star.student),
                comment = CommentEntity.toEntity(star.comment),
            )
        }
    }

    fun toDomain(): Star {
        return Star(
            id = id,
            student = student.toDomain(),
            comment = comment.toDomain(),
        )
    }
}
