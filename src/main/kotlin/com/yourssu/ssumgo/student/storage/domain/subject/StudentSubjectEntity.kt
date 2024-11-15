package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentEntity
import jakarta.persistence.*

@Table(name = "student_subject")
@Entity
class StudentSubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    val student: StudentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    val subject: SubjectEntity,

    @Column(nullable = false)
    val years: Int,

    @Column(nullable = false)
    val semester: Int,
) : BaseEntity() {
    companion object {
        fun toEntity(student: Student, subject: Subject, years: Int, semester: Int): StudentSubjectEntity {
            return StudentSubjectEntity(
                student = StudentEntity.toEntity(student),
                subject = SubjectEntity.toEntity(subject),
                years = years,
                semester = semester
            )
        }
    }
}