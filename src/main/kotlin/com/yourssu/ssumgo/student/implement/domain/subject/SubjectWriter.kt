package com.yourssu.ssumgo.student.implement.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.Student
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SubjectWriter(
    private val subjectRepository: SubjectRepository,
    private val studentSubjectRepository: StudentSubjectRepository,
) {
    @Transactional
    fun save(subject: Subject): Subject {
        return subjectRepository.save(subject)
    }

    @Transactional
    fun saveStudentSubject(student: Student, subject: Subject, years: Int, semester: Int): Long {
        return studentSubjectRepository.save(student, subject, years, semester)
    }
}