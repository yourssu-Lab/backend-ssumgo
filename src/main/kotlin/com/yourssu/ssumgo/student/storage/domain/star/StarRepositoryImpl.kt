package com.yourssu.ssumgo.student.storage.domain.star

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.SortBy
import com.yourssu.ssumgo.student.implement.domain.star.Star
import com.yourssu.ssumgo.student.implement.domain.star.StarRepository
import com.yourssu.ssumgo.student.storage.domain.comment.CommentEntity
import com.yourssu.ssumgo.student.storage.domain.comment.CommentsPage
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class StarRepositoryImpl(
    private val starJpaRepository: StarJpaRepository
) : StarRepository {
    override fun save(star: Star): Star {
        return starJpaRepository.save(StarEntity.toEntity(star)).toDomain()
    }

    override fun get(id: Long): Star {
        return starJpaRepository.get(id)?.toDomain() ?: throw StarNotFoundException()
    }

    override fun getAllByStudentId(studentId: Long, pageNumber: Int, pageSize: Int, sortBy: SortBy): CommentsPage {
        val pageable = getPageable(pageNumber, pageSize, sortBy)
        val comments = starJpaRepository.getAllCommentByStudentId(studentId, pageable)
        return CommentsPage.from(comments)
    }

    override fun deleteByStudentIdAndCommentId(studentId: Long, commentId: Long) {
        starJpaRepository.deleteByCommentId(studentId = studentId, commentId = commentId)
    }

    override fun getCommentsByStar(): List<Comment> {
        return starJpaRepository.getCommentsByStar().map { it.toDomain() }
    }

    private fun getPageable(
        pageNumber: Int,
        pageSize: Int,
        sortBy: SortBy
    ) = PageRequest.of(pageNumber, pageSize, sortBy.direction, "createdDate")
}

interface StarJpaRepository : JpaRepository<StarEntity, Long> {
    @Query("select s from StarEntity s where s.id = :id")
    fun get(id: Long): StarEntity?

    @Query(
        value = "SELECT s.comment FROM StarEntity s " +
                "JOIN s.student " +
                "JOIN s.comment " +
                "WHERE s.student.id = :studentId",
        countQuery = "SELECT COUNT(s) FROM StarEntity s WHERE s.student.id = :studentId"
    )
    fun getAllCommentByStudentId(studentId: Long, pageable: Pageable): Page<CommentEntity>

    @Modifying
    @Transactional
    @Query("delete from StarEntity s where s.comment.id = :commentId and s.student.id = :studentId")
    fun deleteByCommentId(studentId: Long, commentId: Long)

    @Query(
        "select c from CommentEntity c " +
                "left join StarEntity s " +
                "on s.comment = c " +
                "group by c " +
                "order by count(s) desc " +
                "limit 10"
    )
    fun getCommentsByStar(): List<CommentEntity>
}

class StarNotFoundException : NotFoundException(message = "보관함에 등록되지 않은 좋아요입니다.")
