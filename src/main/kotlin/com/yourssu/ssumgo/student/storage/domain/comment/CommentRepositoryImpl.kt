package com.yourssu.ssumgo.student.storage.domain.comment

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.comment.CommentRepository
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.storage.domain.posts.PostsEntity
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

    override fun existsComment(posts: Posts): Boolean {
        return commentJpaRepository.existsByPosts(PostsEntity.toEntity(posts))
    }


    override fun findAllBySubject(
        subjectId: Long,
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ): CommentsPage {
        val pageable = PageRequest.of(pageNumber, pageSize, sortBy.direction, "createdDate")
        val comments =
            commentJpaRepository.findAllByPostsIdAndMenteeId(
                subjectId = subjectId,
                pageable = pageable
            )
        return CommentsPage.from(comments)
    }

    override fun findAllByMentee(menteeId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        val comments = commentJpaRepository.findAllByMentee2(
            menteeId = menteeId,
            pageable = PageRequest.of(pageNumber, pageSize, sortBy.direction, "createdDate")
        )
        return CommentsPage.from(comments)
    }
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

    @Query("select c from CommentEntity c where c.posts.subject.id = :subjectId")
    fun findAllByPostsIdAndMenteeId(subjectId: Long, pageable: Pageable): Page<CommentEntity>

    @Query("select (count(c) > 0) from CommentEntity c where c.posts = ?1")
    fun existsByPosts(posts: PostsEntity): Boolean

    @Query("select c from CommentEntity c where c.posts.mentee.id = :menteeId")
    fun findAllByMentee2(menteeId: Long, pageable: PageRequest): Page<CommentEntity>
}

class CommentNotFoundException : NotFoundException(message = "해당하는 댓글이 없습니다.")
