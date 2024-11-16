package com.yourssu.ssumgo.student.implement.domain.posts

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostsWriter(
    private val postsRepository: PostsRepository,
) {
    @Transactional
    fun savePost(posts: Posts): Posts {
        return postsRepository.save(posts)
    }
}