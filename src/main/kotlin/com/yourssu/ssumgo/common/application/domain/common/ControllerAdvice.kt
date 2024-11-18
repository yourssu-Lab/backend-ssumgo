package com.yourssu.ssumgo.common.application.domain.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ErrorResponse.from(InternalServerErrorResponse()))
    }

    @ExceptionHandler(BadRequestErrorResponse::class)
    fun handleBadRequest(e: BadRequestErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.from(e))
    }

    @ExceptionHandler(NotFoundErrorResponse::class)
    fun handleNotFound(e: NotFoundErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.from(e))
    }

    @ExceptionHandler(UnauthorizedErrorResponse::class)
    fun handleUnauthorized(e: UnauthorizedErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.from(e))
    }

    @ExceptionHandler(ForbiddenErrorResponse::class)
    fun handleForbidden(e: ForbiddenErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse.from(e))
    }

    @ExceptionHandler
    fun handleValidException(
        bindingResult: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {
        val errorMessage = bindingResult.fieldErrors
            .map { it.defaultMessage ?: "Unknown validation error" }
            .joinToString(", ") { "Invalid Input: [$it]" }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage
            ))
    }
}

data class ErrorResponse(
    val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")),
    val status: Int,
    val message: String,
) {
    companion object {
        fun from(e: Error): ErrorResponse {
            return ErrorResponse(
                status = e.status.value(),
                message = e.message,
            )
        }
    }
}

abstract class Error(
    val status: HttpStatus,
    override val message: String
) : Exception()

open class InternalServerErrorResponse(
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message: String = "알 수 없는 에러가 발생했습니다."
) : Error(status, message) {
}

open class BadRequestErrorResponse(
    status: HttpStatus = HttpStatus.BAD_REQUEST,
    message: String = "잘못된 요청입니다.",
) : Error(status, message) {
}

open class NotFoundErrorResponse(
    status: HttpStatus = HttpStatus.NOT_FOUND,
    message: String = "존재하지 않는 리소스입니다.",
) : Error(status, message) {
}

open class UnauthorizedErrorResponse(
    status: HttpStatus = HttpStatus.UNAUTHORIZED,
    message: String = "인증되지 않은 사용자입니다.",
) : Error(status, message) {
}

open class ForbiddenErrorResponse(
    status: HttpStatus = HttpStatus.FORBIDDEN,
    message: String = "권한이 없습니다.",
) : Error(status, message) {
}