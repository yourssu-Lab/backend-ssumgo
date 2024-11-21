package com.yourssu.ssumgo.student.implement.domain.star

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StarWriter(
    private val starRepository: StarRepository
) {
    @Transactional
    fun save(star: Star): Star {
        return starRepository.save(star)
    }

    @Transactional
    fun deleteByComment(studentId: Long, commentId: Long) {
        starRepository.deleteByStudentIdAndCommentId(studentId = studentId, commentId = commentId)
    }
}