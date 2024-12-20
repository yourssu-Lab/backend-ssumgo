package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.PostsReader
import com.yourssu.ssumgo.student.implement.domain.posts.PostsWriter
import com.yourssu.ssumgo.student.implement.domain.student.StudentReader
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectReader
import org.springframework.stereotype.Service

@Service
class PostsService(
    private val postsReader: PostsReader,
    private val postsWriter: PostsWriter,
    private val studentReader: StudentReader,
    private val subjectReader: SubjectReader
) {
    fun savePosts(command: PostsCreatedCommand): PostsResponse {
        val mentee = studentReader.get(command.menteeId)
        val subject = subjectReader.get(command.subjectId)
        return PostsResponse.from(postsWriter.save(command.toDomain(mentee, subject)))
    }

    fun getPostsById(id: Long): PostsResponse {
        return PostsResponse.from(postsReader.get(id))
    }

    fun findAllPostsBySubjectId(command: PostsFoundBySubjectCommand): PostsPageResponse {
        val subject = subjectReader.get(command.subjectId)
        val postsPage = postsReader.findAllBySubject(
            subjectId = subject.id!!,
            pageNumber = command.page,
            sortBy = command.sortBy,
            size = command.size
        )
        return PostsPageResponse(
            postsList = postsPage.content.map { PostsResponse.from(it) },
            totalPages = postsPage.totalPages,
            totalCount = postsPage.totalElements.toInt(),
            hasNext = postsPage.hasNext,
        )
    }
}