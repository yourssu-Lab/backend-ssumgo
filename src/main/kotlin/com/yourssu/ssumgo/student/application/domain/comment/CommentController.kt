package com.yourssu.ssumgo.student.application.domain.comment

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentCreatedCommand
import com.yourssu.ssumgo.student.business.domain.comment.CommentResponse
import com.yourssu.ssumgo.student.business.domain.comment.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun crateComment(
        @StudentId mentorId: Long,
        @PathVariable postId: Long,
        @RequestBody request: CommentCreateRequest
    ): ResponseEntity<Response<CommentResponse>> {
        val response = commentService.saveComment(
            request.toCommand(
                mentorId = mentorId,
                postId = postId
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(Response(result = response))
    }

    @GetMapping("/{commentId}")
    fun get(@PathVariable postId: Long, @PathVariable commentId: Long): ResponseEntity<Response<CommentResponse>> {
        val response = commentService.getCommentById(postId = postId, commentId = commentId)
        return ResponseEntity.ok(Response(result = response))
    }
}

data class CommentCreateRequest(
    val title: String,
    val content: String,
) {
    fun toCommand(mentorId: Long, postId: Long): CommentCreatedCommand {
        return CommentCreatedCommand(
            mentorId = mentorId,
            postsId = postId,
            title = title,
            content = content
        )
    }
}
