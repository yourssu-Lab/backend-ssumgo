package com.yourssu.ssumgo.student.implement.domain.star

import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StarReader(
    private val starRepository: StarRepository
) {
    @Transactional(readOnly = true)
    fun getAllByStudent(studentId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        return starRepository.getAllByStudentId(
            studentId = studentId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }
}