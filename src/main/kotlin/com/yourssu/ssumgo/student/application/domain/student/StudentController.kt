package com.yourssu.ssumgo.student.application.domain.student

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.business.domain.student.CommentFoundByMenteeCommand
import com.yourssu.ssumgo.student.business.domain.student.StudentResponse
import com.yourssu.ssumgo.student.business.domain.student.StudentService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService,
) {
    @GetMapping
    fun getStudent(@StudentId id: Long): ResponseEntity<Response<StudentResponse>> {
        return ResponseEntity.ok(Response(result = studentService.getStudent(id)))
    }

    @GetMapping("/comments")
    fun getCommentsEtcBySubject(
        @StudentId menteeId: Long,
        @Positive @RequestParam(defaultValue = "1") page: Int,
        @NotBlank @RequestParam(defaultValue = "latest") sortBy: String,
        @Positive @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Response<CommentsPageResponse>> {
        val command = CommentFoundByMenteeCommand(
            menteeId = menteeId,
            page = page,
            sortBy = SortBy.of(sortBy),
            size = size,
        )
        val response = studentService.findAllCommentsByMentee(command)
        return ResponseEntity.ok(Response(result = response))
    }
}
