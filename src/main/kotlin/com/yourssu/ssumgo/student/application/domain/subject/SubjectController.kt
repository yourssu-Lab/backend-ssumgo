package com.yourssu.ssumgo.student.application.domain.subject

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentFoundBySubjectCommand
import com.yourssu.ssumgo.student.business.domain.comment.CommentService
import com.yourssu.ssumgo.student.business.domain.comment.CommentsPageResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectResponse
import com.yourssu.ssumgo.student.business.domain.subject.SubjectService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subjects")
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
        @RequestParam(defaultValue = "2024") year: Int,
        @RequestParam(defaultValue = "2") semester: Int,
    ): ResponseEntity<Response<List<SubjectResponse>>> {
        return ResponseEntity.ok(Response(result = subjectService.getSubjectsByStudent(studentId, year, semester)))
    }

    @GetMapping("/{subjectId}/comments")
    fun getCommentsBySubject(
        @StudentId menteeId: Long,
        @PathVariable subjectId: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "latest") sortBy: String,
        @RequestParam(defaultValue = "10") size: Int,
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
        @PathVariable subjectId: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "latest") sortBy: String,
        @RequestParam(defaultValue = "10") size: Int,
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