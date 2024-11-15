package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentReader: StudentReader,
) {
    fun getStudent(studentId: Long): StudentResponse {
        val student = studentReader.getStudent(studentId)
        return StudentResponse.from(student)
    }
}