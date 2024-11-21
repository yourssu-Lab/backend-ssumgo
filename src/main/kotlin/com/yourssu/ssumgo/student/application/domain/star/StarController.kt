package com.yourssu.ssumgo.student.application.domain.star

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentResponse
import com.yourssu.ssumgo.student.business.domain.star.StarCreatedCommand
import com.yourssu.ssumgo.student.business.domain.star.StarService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/drawers")
@Validated
class StarController(
    private val starService: StarService
) {
    @PostMapping
    fun save(@StudentId studentId: Long, @RequestBody request: StarCreateRequest): ResponseEntity<Response<CommentResponse>> {
        val response = starService.saveStar(StarCreatedCommand(commentId = request.commentId, studentId = studentId))
        return ResponseEntity.status(HttpStatus.CREATED).body(Response(result = response))
    }

    @GetMapping("/students")
    fun getAllByStudent(
        @StudentId studentId: Long,
        @Positive @RequestParam(defaultValue = "1") page: Int,
        @NotBlank @RequestParam(defaultValue = "latest") sortBy: String,
        @Positive @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Response<CommentsPage>> {
        val response = starService.getAllByStudent(
            studentId = studentId,
            pageNumber = page,
            pageSize = size,
            sortBy = SortBy.of(sortBy)
        )
        return ResponseEntity.ok(Response(result = response))
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteByComment(@StudentId studentId: Long, @Positive @PathVariable commentId: Long): ResponseEntity<Void> {
        starService.deleteByComment(studentId = studentId, commentId = commentId)
        return ResponseEntity.noContent().build()
    }
}

data class StarCreateRequest(
    @Positive
    val commentId: Long
)