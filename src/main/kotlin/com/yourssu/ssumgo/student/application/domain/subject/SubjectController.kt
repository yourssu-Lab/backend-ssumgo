package com.yourssu.ssumgo.student.application.domain.subject

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentFoundBySubjectCommand
import com.yourssu.ssumgo.student.business.domain.comment.CommentService
import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.business.domain.subject.StudentSubjectCreatedCommand
import com.yourssu.ssumgo.student.business.domain.subject.SubjectCreatedCommand
import com.yourssu.ssumgo.student.business.domain.subject.SubjectResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Range
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subjects")
@Validated
class SubjectController(
    private val subjectService: SubjectService,
    private val commentService: CommentService,
) {
    @PostMapping
    fun createSubject(@StudentId studentId: Long, @Valid @RequestBody request: SubjectCreatedRequest): ResponseEntity<Response<SubjectResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response(result = subjectService.saveSubject(request.toCommand())))
    }

    @PostMapping("/students")
    fun createStudentSubject(@StudentId studentId: Long, @Valid @RequestBody request: StudentSubjectCreatedRequest): ResponseEntity<Response<SubjectResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response(result = subjectService.saveStudentSubject(request.toCommand(studentId))))
    }

    @GetMapping
    fun getSubjects(): ResponseEntity<Response<List<SubjectResponse>>> {
        return ResponseEntity.ok(Response(result = subjectService.getAllSubjects()))
    }

    @GetMapping("/students")
    fun getSubjectsByStudent(
        @StudentId studentId: Long,
        @Positive @RequestParam(defaultValue = "2024") year: Int,
        @Range(min = 1, max = 2) @RequestParam(defaultValue = "2") semester: Int,
    ): ResponseEntity<Response<List<SubjectResponse>>> {
        return ResponseEntity.ok(Response(result = subjectService.getSubjectsByStudent(studentId, year, semester)))
    }

    @GetMapping("/{subjectId}/comments")
    fun getCommentsBySubject(
        @Positive @PathVariable subjectId: Long,
        @Positive @RequestParam(defaultValue = "1") page: Int,
        @NotBlank @RequestParam(defaultValue = "latest") sortBy: String,
        @Positive @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "") q: String,
    ): ResponseEntity<Response<CommentsPageResponse>> {
        val command = CommentFoundBySubjectCommand(
            subjectId = subjectId,
            page = page,
            sortBy = SortBy.of(sortBy),
            size = size,
            query = q,
        )
        val response = commentService.findAllCommentsBySubject(command)
        return ResponseEntity.ok(Response(result = response))
    }
}

data class StudentSubjectCreatedRequest(
    @Positive
    val subjectId: Long,

    @Positive
    val years: Int,

    @Range(min = 1, max = 2)
    val semester: Int,
) {
    fun toCommand(studentId: Long): StudentSubjectCreatedCommand {
        return StudentSubjectCreatedCommand(
            studentId = studentId,
            subjectId = subjectId,
            years = years,
            semester = semester,
        )
    }
}

data class SubjectCreatedRequest(
    @NotBlank
    val subjectName: String,

    @NotBlank
    val professorName: String,

    @NotBlank
    val completion: String,

    @Positive
    val subjectCode: Int,

    @NotBlank
    val department: String,

    @Positive
    val time: Int,

    @Positive
    val credit: Int,
) {
    fun toCommand(): SubjectCreatedCommand {
        return SubjectCreatedCommand(
            subjectName = subjectName,
            professorName = professorName,
            completion = completion,
            subjectCode = subjectCode,
            department = department,
            time = time,
            credit = credit,
        )
    }

}
