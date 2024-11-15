package com.yourssu.ssumgo.student.implement.domain.subject

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SubjectReader(
    private val subjectRepository: SubjectRepository,
    private val studentSubjectRepository: StudentSubjectRepository
) {
    @Transactional(readOnly = true)
    fun getSubjects(studentId: Long, years: Int, semester: Int): List<Subject> {
        return studentSubjectRepository.getSubjects(studentId, years, semester)
    }

    @Transactional(readOnly = true)
    fun getAllSubjects(): List<Subject> {
        return subjectRepository.getAllSubjects()
    }
}