package com.yourssu.ssumgo.student.application.domain.evaluation

import com.yourssu.ssumgo.common.application.domain.common.Response
import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.student.business.domain.evaluation.EvaluationCreatedCommand
import com.yourssu.ssumgo.student.business.domain.evaluation.EvaluationResponse
import com.yourssu.ssumgo.student.business.domain.evaluation.EvaluationService
import com.yourssu.ssumgo.student.implement.domain.evaluation.Rating
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/evaluations")
@Validated
class EvaluationController(
    private val evaluationService: EvaluationService,
) {
    @PostMapping
    fun createEvaluation(@StudentId menteeId: Long, @Valid @RequestBody request: EvaluationCreateRequest):
            ResponseEntity<Response<EvaluationResponse>> {
        val response = evaluationService.saveEvaluation(request.toCommand(menteeId))
        return ResponseEntity.status(HttpStatus.CREATED).body(Response(result = response))
    }

    @GetMapping("/comments/{commentId}")
    fun getByComment(@Positive @PathVariable commentId: Long): ResponseEntity<Response<EvaluationResponse>> {
        val response = evaluationService.getByComment(commentId)
        return ResponseEntity.ok(Response(result = response))
    }
}

data class EvaluationCreateRequest(
    @Positive
    val commentId: Long,

    @Positive
    val rating: Int,

    @NotBlank
    val additionalInfo: String,
) {
    fun toCommand(menteeId: Long): EvaluationCreatedCommand {
        return EvaluationCreatedCommand(
            menteeId = menteeId,
            commentId = commentId,
            rating = Rating.of(rating),
            additionalInfo = additionalInfo,
        )
    }
}