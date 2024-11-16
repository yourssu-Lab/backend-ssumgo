package com.yourssu.ssumgo.student.implement.domain.posts

import com.yourssu.ssumgo.student.storage.domain.posts.PostsPage

interface PostsRepository {
    fun save(posts: Posts): Posts
    fun findAllBySubjectId(subjectId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): PostsPage
    fun get(id: Long): Posts
}