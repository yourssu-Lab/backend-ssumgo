package com.yourssu.ssumgo.student.storage.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.PostsRepository
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class PostsRepositoryImpl(
    private val postsJpaRepository: PostsJpaRepository,
): PostsRepository {
    override fun save(posts: Posts): Posts {
        return postsJpaRepository.save(PostsEntity.toEntity(posts)).toDomain()
    }

    override fun get(id: Long): Posts {
        return postsJpaRepository.get(id)?.toDomain()
            ?: throw IllegalArgumentException("해당하는 게시글이 없습니다.")
    }

    override fun findAllBySubjectId(subjectId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): PostsPage {
        val pageable = PageRequest.of(pageNumber, pageSize, sortBy.direction, "createdDate")
        return PostsPage.from(postsJpaRepository.findAllBySubjectId(subjectId = subjectId, pageable = pageable))
    }
}

data class PostsPage(
    val content: List<Posts>,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
) {
    companion object {
        fun from(page: Page<PostsEntity>): PostsPage {
            return PostsPage(
                content = page.content.map { it.toDomain() },
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                hasNext = page.hasNext()
            )
        }
    }
}

interface PostsJpaRepository : JpaRepository<PostsEntity, Long> {
    @Query(
        value = "SELECT p FROM PostsEntity p " +
                "JOIN FETCH p.subject " +
                "WHERE p.subject.id = :subjectId",
        countQuery = "SELECT COUNT(p) FROM PostsEntity p WHERE p.subject.id = :subjectId"
    )
    fun findAllBySubjectId(subjectId: Long, pageable: Pageable): Page<PostsEntity>

    @Query("SELECT p FROM PostsEntity p WHERE p.id = :id")
    fun get(id: Long): PostsEntity?
}
