package com.yourssu.ssumgo.student.implement.domain.posts

import com.yourssu.ssumgo.student.storage.domain.posts.PostsPage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostsReader(
    private val postsRepository: PostsRepository,
) {
    @Transactional(readOnly = true)
    fun getById(id: Long): Posts {
        return postsRepository.get(id)
    }

    @Transactional(readOnly = true)
    fun findAllPostsBySubject(subjectId: Long, pageNumber: Int, sortBy: SortBy = SortBy.LATEST, size: Int): PostsPage {
        return postsRepository.findAllBySubjectId(
            subjectId = subjectId,
            pageNumber = pageNumber - 1,
            pageSize = size,
            sortBy = sortBy
        )
    }

    @Transactional(readOnly = true)
    fun findAllPostsByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): PostsPage {
        return postsRepository.findAllByMenteeId(
            menteeId = menteeId,
            pageNumber = pageNumber - 1,
            pageSize = pageSize,
            sortBy = sortBy
        )
    }
}