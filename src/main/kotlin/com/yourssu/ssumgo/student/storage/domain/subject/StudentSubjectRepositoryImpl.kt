package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.subject.StudentSubjectRepository
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.storage.domain.student.StudentRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class StudentSubjectRepositoryImpl(
    private val studentSubjectJpaRepository: StudentSubjectJpaRepository,
    private val studentRepositoryImpl: StudentRepositoryImpl,
    private val subjectRepositoryImpl: SubjectRepositoryImpl,
) : StudentSubjectRepository {
    override fun getSubjects(studentId: Long, years: Int, semester: Int): List<Subject> {
        val subjects = studentSubjectJpaRepository.getSubjects(studentId, years, semester)
        return subjects.map { it.toDomain() }
    }

    override fun save(student: Student, subject: Subject, years: Int, semester: Int): Long {
        val savedStudent = studentRepositoryImpl.get(student.id!!)
        val savedSubject = subjectRepositoryImpl.get(subject.id!!)
        val studentSubjectEntity = studentSubjectJpaRepository.save(
            StudentSubjectEntity.toEntity(
                student = savedStudent,
                subject = savedSubject,
                years = years,
                semester = semester,
            )
        )
        return studentSubjectEntity.id!!
    }

    fun getSubject(id: Long): StudentSubjectEntity {
        return studentSubjectJpaRepository.get(id)
            ?: throw IllegalArgumentException("해당하는 수강과목이 없습니다.")
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

    @Query("select s from StudentSubjectEntity s where s.id = :id")
    fun get(id: Long): StudentSubjectEntity?
}
