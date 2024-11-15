package com.yourssu.ssumgo.student.application.domain.student

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.student.StudentResponse
import com.yourssu.ssumgo.student.business.domain.student.StudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService,
) {
    @GetMapping
    fun getStudent(@StudentId id: Long): ResponseEntity<Response<StudentResponse>> {
        return ResponseEntity.ok(Response(result = studentService.getStudent(id)))
    }
}