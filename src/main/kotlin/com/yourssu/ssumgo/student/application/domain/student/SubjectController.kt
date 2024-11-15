package com.yourssu.ssumgo.student.application.domain.student

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.subject.SubjectResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subjects")
class SubjectController(
    private val subjectService: SubjectService
) {
    @GetMapping
    fun getSubjects(): ResponseEntity<Response<List<SubjectResponse>>> {
        return ResponseEntity.ok(Response(result = subjectService.getAllSubjects()))
    }

    @GetMapping("/students")
    fun getSubjectsByStudent(
        @StudentId studentId: Long,
        @RequestParam(defaultValue = "2024") year: Int,
        @RequestParam(defaultValue = "2") semester: Int,
    ): ResponseEntity<Response<List<SubjectResponse>>> {
        return ResponseEntity.ok(Response(result = subjectService.getSubjectsByStudent(studentId, year, semester)))
    }
}