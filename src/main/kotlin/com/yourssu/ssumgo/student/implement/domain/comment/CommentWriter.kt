package com.yourssu.ssumgo.student.implement.domain.comment

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentWriter(
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun save(comment: Comment): Comment {
        return commentRepository.save(comment)
    }
}