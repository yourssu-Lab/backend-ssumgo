package com.yourssu.ssumgo.student.application.domain.subject

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentFoundBySubjectCommand
import com.yourssu.ssumgo.student.business.domain.comment.CommentService
import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Range
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
        @StudentId menteeId: Long,
        @Positive @PathVariable subjectId: Long,
        @Positive @RequestParam(defaultValue = "1") page: Int,
        @NotBlank @RequestParam(defaultValue = "latest") sortBy: String,
        @Positive @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Response<CommentsPageResponse>> {
        val command = CommentFoundBySubjectCommand(
            menteeId = menteeId,
            subjectId = subjectId,
            page = page,
            sortBy = SortBy.of(sortBy),
            size = size,
        )
        val response = commentService.findAllCommentsByMentee(command)
        return ResponseEntity.ok(Response(result = response))
    }

    @GetMapping("/{subjectId}/comments/etc")
    fun getCommentsEtcBySubject(
        @StudentId menteeId: Long,
        @Positive @PathVariable subjectId: Long,
        @Positive @RequestParam(defaultValue = "1") page: Int,
        @NotBlank @RequestParam(defaultValue = "latest") sortBy: String,
        @Positive @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Response<CommentsPageResponse>> {
        val command = CommentFoundBySubjectCommand(
            menteeId = menteeId,
            subjectId = subjectId,
            page = page,
            sortBy = SortBy.of(sortBy),
            size = size,
        )
        val response = commentService.findAllCommentsByNotMentee(command)
        return ResponseEntity.ok(Response(result = response))
    }
}