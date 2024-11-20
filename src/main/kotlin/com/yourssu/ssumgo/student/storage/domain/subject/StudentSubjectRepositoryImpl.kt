package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.StudentSubjectRepository
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class StudentSubjectRepositoryImpl(
    private val studentSubjectJpaRepository: StudentSubjectJpaRepository,
) : StudentSubjectRepository {
    override fun save(student: Student, subject: Subject, years: Int, semester: Int): Subject {
        val studentSubject = studentSubjectJpaRepository.save(
            StudentSubjectEntity.toEntity(
                student = student,
                subject = subject,
                years = years,
                semester = semester,
            )
        )
        return studentSubject.toDomain()
    }

    override fun existsStudentSubject(studentId: Long, subjectId: Long, years: Int, semester: Int): Boolean {
        return studentSubjectJpaRepository.existsStudentSubject(
            studentId = studentId,
            subjectId = subjectId,
            years = years,
            semester = semester
        )
    }

    override fun getSubjects(studentId: Long, years: Int, semester: Int): List<Subject> {
        val subjects = studentSubjectJpaRepository.getSubjects(
            studentId = studentId,
            years = years,
            semester = semester
        )
        return subjects.map { it.toDomain() }
    }
}

interface StudentSubjectJpaRepository : JpaRepository<StudentSubjectEntity, Long> {
    @Query(
        "select s.subject from StudentSubjectEntity s " +
                "where s.student.id = :studentId and " +
                "s.years = :years and " +
                "s.semester = :semester"
    )
    fun getSubjects(studentId: Long, years: Int, semester: Int): List<SubjectEntity>

    @Query(
        "select (count(s) > 0) from StudentSubjectEntity s " +
                "where s.student.id = :studentId and " +
                "s.subject.id = :subjectId and " +
                "s.years = :years and " +
                "s.semester = :semester"
    )
    fun existsStudentSubject(studentId: Long, subjectId: Long, years: Int, semester: Int): Boolean
}
