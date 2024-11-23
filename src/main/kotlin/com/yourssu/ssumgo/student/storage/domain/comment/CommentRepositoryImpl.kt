package com.yourssu.ssumgo.student.storage.domain.comment

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.comment.CommentRepository
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(
    private val commentJpaRepository: CommentJpaRepository,
) : CommentRepository {
    override fun save(comment: Comment): Comment {
        return commentJpaRepository.save(CommentEntity.toEntity(comment)).toDomain()
    }

    override fun get(postId: Long, commentId: Long): Comment {
        return commentJpaRepository.get(postId = postId, commentId = commentId)?.toDomain()
            ?: throw CommentNotFoundException()
    }

    override fun get(commentId: Long): Comment {
        return commentJpaRepository.get(commentId)?.toDomain()
            ?: throw CommentNotFoundException()
    }

    override fun existsComment(postId: Long): Boolean {
        return commentJpaRepository.existsByPostId(postId)
    }

    override fun findAllBySubject(
        subjectId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage {
        val comments = commentJpaRepository.findAllBySubjectId(
            subjectId = subjectId,
            pageable = getPageable(pageNumber, pageSize, sortBy)
        )
        return CommentsPage.from(comments)
    }

    override fun findAllBySubjectWithSearch(
        subjectId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy,
        query: String
    ): CommentsPage {
        val comments = commentJpaRepository.findAllBySubjectIdWithQuery(
            subjectId = subjectId,
            query = query,
            pageable = getPageable(pageNumber, pageSize, sortBy)
        )
        return CommentsPage.from(comments)
    }

    override fun findAllByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        val comments = commentJpaRepository.findAllByMenteeId(
            menteeId = menteeId,
            pageable = getPageable(pageNumber, pageSize, sortBy),
        )
        return CommentsPage.from(comments)
    }

    private fun getPageable(
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ) = PageRequest.of(pageNumber, pageSize, sortBy.direction, "createdDate")
}

data class CommentsPage(
    val content: List<Comment>,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
) {
    companion object {
        fun from(page: Page<CommentEntity>): CommentsPage {
            return CommentsPage(
                content = page.content.map { it.toDomain() },
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                hasNext = page.hasNext()
            )
        }
    }
}

interface CommentJpaRepository : JpaRepository<CommentEntity, Long> {
    @Query("select c from CommentEntity c where c.posts.id = :postId and c.id = :commentId")
    fun get(postId: Long, commentId: Long): CommentEntity?

    @Query("select c from CommentEntity c where c.id = :commentId")
    fun get(commentId: Long): CommentEntity?

    @Query(
        value = "SELECT c FROM CommentEntity c " +
                "JOIN c.posts p " +
                "WHERE p.subject.id = :subjectId",
        countQuery = "SELECT COUNT(c) FROM CommentEntity c WHERE c.posts.subject.id = :subjectId"
    )
    fun findAllBySubjectId(subjectId: Long, pageable: Pageable): Page<CommentEntity>

    @Query("select (count(c) > 0) from CommentEntity c where c.posts.id = :postId")
    fun existsByPostId(postId: Long): Boolean

    @Query(
        value = "SELECT c FROM CommentEntity c " +
                "JOIN c.posts p " +
                "WHERE p.subject.id = :subjectId",
        countQuery = "SELECT COUNT(c) FROM CommentEntity c " +
                "WHERE c.posts.subject.id = :subjectId " +
                "AND ((c.content LIKE %:query%) OR (c.title LIKE %:query%) " +
                "OR (c.posts.title LIKE %:query%) OR (c.posts.content LIKE %:query%))"
    )
    fun findAllBySubjectIdWithQuery(subjectId: Long, query: String, pageable: Pageable): Page<CommentEntity>

    @Query(
        value = "SELECT c FROM CommentEntity c " +
                "JOIN c.posts p " +
                "WHERE p.mentee.id = :menteeId",
        countQuery = "SELECT COUNT(c) FROM CommentEntity c WHERE c.posts.mentee.id = :menteeId"
    )
    fun findAllByMenteeId(menteeId: Long, pageable: Pageable): Page<CommentEntity>
}

class CommentNotFoundException : NotFoundException(message = "해당하는 댓글이 없습니다.")
