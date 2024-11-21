package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.business.domain.student.CommentFoundByMenteeCommand
import com.yourssu.ssumgo.student.business.domain.student.PostsFoundByMenteeCommand
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

enum class PageFixture(
    val page: Int,
    val size: Int,
    val sortBy: SortBy,
) {
    PAGE_LATEST(
        page = 1,
        size = 10,
        sortBy = SortBy.LATEST
    ),
    PAGE_EARLIEST(
        page = 1,
        size = 10,
        sortBy = SortBy.EARLIEST
    );

    fun toDomain(): Pageable {
        return PageRequest.of(page, size)
    }

    fun toPostsFoundByMenteeCommand(menteeId: Long): PostsFoundByMenteeCommand {
        return PostsFoundByMenteeCommand(
            menteeId = menteeId,
            page = page,
            sortBy = sortBy,
            size = size
        )
    }

    fun toCommentFoundByMenteeCommand(menteeId: Long): CommentFoundByMenteeCommand {
        return CommentFoundByMenteeCommand(
            menteeId = menteeId,
            page = page,
            sortBy = sortBy,
            size = size
        )
    }
}