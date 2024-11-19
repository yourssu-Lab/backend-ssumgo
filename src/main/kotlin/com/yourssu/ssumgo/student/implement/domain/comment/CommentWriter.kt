package com.yourssu.ssumgo.student.implement.domain.comment

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentWriter(
    private val commentRepository: CommentRepository,
) {
    @Transactional
    fun save(comment: Comment): Comment {
        if (commentRepository.existsComment(comment.posts)) {
            throw CommentExistException()
        }
        return commentRepository.save(comment)
    }
}

class CommentExistException() : RuntimeException(message = "해당 질문에 이미 답변이 존재합니다.")