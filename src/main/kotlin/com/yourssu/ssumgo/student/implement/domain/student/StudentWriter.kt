package com.yourssu.ssumgo.student.implement.domain.student

import org.springframework.stereotype.Component

@Component
class StudentWriter(
    private val studentRepository: StudentRepository,
) {
    fun signIn(student: Student): Student {
        return studentRepository.saveOrUpdate(student)
    }
}