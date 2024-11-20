package com.yourssu.ssumgo.student.implement.domain.subject

import com.yourssu.ssumgo.common.application.domain.common.ConflictException
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
    fun saveStudentSubject(student: Student, subject: Subject, years: Int, semester: Int): Subject {
        if (studentSubjectRepository.existsStudentSubject(
                studentId = student.id!!,
                subjectId = subject.id!!,
                years = years,
                semester = semester,
            )
        ) {
            throw StudentSubjectExistException()
        }
        return studentSubjectRepository.save(
            student = student,
            subject = subject,
            years = years,
            semester = semester
        )
    }
}

class StudentSubjectExistException : ConflictException(message = "해당 학기에 이미 등록된 수강과목입니다.")
