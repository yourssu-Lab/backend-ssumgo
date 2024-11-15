package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import jakarta.persistence.*

@Table(name = "subject")
@Entity
class SubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val subjectName: String,

    @Column(nullable = false)
    val professorName: String,

    @Column(nullable = false)
    val completion: String,

    @Column(nullable = false, unique = true)
    val subjectCode: Int,

    @Column(nullable = false)
    val department: String,

    @Column(nullable = false)
    val time: Int,

    @Column(nullable = false)
    val credit: Int,
    ) {
    companion object {
        fun toEntity(subject: Subject): SubjectEntity {
            return SubjectEntity(
                id = subject.id,
                subjectName = subject.subjectName,
                professorName = subject.professorName,
                completion = subject.completion,
                subjectCode = subject.subjectCode,
                department = subject.department,
                time = subject.time,
                credit = subject.credit,
                )
        }
    }

    fun toDomain(): Subject {
        return Subject(
            id = id,
            subjectName = subjectName,
            professorName = professorName,
            completion = completion,
            subjectCode = subjectCode,
            department = department,
            time = time,
            credit = credit,
        )
    }
}