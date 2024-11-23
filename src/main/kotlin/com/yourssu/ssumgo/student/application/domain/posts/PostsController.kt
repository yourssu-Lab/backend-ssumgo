package com.yourssu.ssumgo.student.application.domain.posts

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.comment.CommentResponse
import com.yourssu.ssumgo.student.business.domain.posts.*
import com.yourssu.ssumgo.student.business.domain.star.StarService
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
@Validated
class PostsController(
    private val postsService: PostsService,
    private val starService: StarService,
) {
    @PostMapping
    fun savePosts(
        @StudentId studentId: Long,
        @Valid @RequestBody request: PostsCreatedRequest
    ): ResponseEntity<Response<PostsResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response(result = postsService.savePosts(request.toCommand(studentId))))
    }

    @GetMapping("/{postId}")
    fun getPostsById(@Positive @PathVariable postId: Long): ResponseEntity<Response<PostsResponse>> {
        return ResponseEntity.ok(Response(result = postsService.getPostsById(postId)))
    }

    @GetMapping("/subjects/{subjectId}")
    fun getPostsBySubjectId(
        @PathVariable subjectId: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "latest") sortBy: String,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Response<PostsPageResponse>> {
        val command = PostsFoundBySubjectCommand(
            subjectId = subjectId,
            sortBy = SortBy.of(sortBy),
            page = page,
            size = size
        )
        val result = postsService.findAllPostsBySubjectId(command)
        return ResponseEntity.ok(Response(result = result))
    }

    @GetMapping("/comments/popular")
    fun getCommentsByStar(): ResponseEntity<Response<List<CommentResponse>>> {
        return ResponseEntity.ok(Response(result = starService.getAllCommentByStar()))
    }
}

data class PostsCreatedRequest(
    val subjectId: Long,
    val title: String,
    val content: String,
) {
    fun toCommand(menteeId: Long): PostsCreatedCommand {
        return PostsCreatedCommand(
            menteeId = menteeId,
            subjectId = subjectId,
            title = title,
            content = content
        )
    }
}
