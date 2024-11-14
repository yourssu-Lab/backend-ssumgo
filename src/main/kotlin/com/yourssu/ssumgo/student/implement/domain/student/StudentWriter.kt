package com.yourssu.ssumgo.student.implement.domain.student

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StudentWriter(
    private val studentRepository: StudentRepository,
) {
    @Transactional
    fun signIn(student: Student): Student {
        return studentRepository.saveOrUpdate(student)
    }
}