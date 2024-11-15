package com.yourssu.ssumgo.student.implement.domain.student

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StudentReader(
    private val studentRepository: StudentRepository
) {
    @Transactional(readOnly = true)
    fun getStudent(studentId: Long): Student {
        return studentRepository.get(studentId)
    }
}
