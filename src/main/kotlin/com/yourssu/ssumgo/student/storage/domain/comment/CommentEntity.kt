package com.yourssu.ssumgo.student.storage.domain.comment

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.storage.domain.posts.PostsEntity
import com.yourssu.ssumgo.student.storage.domain.student.StudentEntity
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    val mentor: StudentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    val posts: PostsEntity,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,
) : BaseEntity(
) {
    companion object {
        fun toEntity(comment: Comment): CommentEntity {
            return CommentEntity(
                id = comment.id,
                mentor = StudentEntity.toEntity(comment.mentor),
                posts = PostsEntity.toEntity(comment.posts),
                title = comment.title,
                content = comment.content,
            )
        }
    }

    fun toDomain(): Comment {
        return Comment(
            id = id,
            mentor = mentor.toDomain(),
            posts = posts.toDomain(),
            title = title,
            content = content,
        )
    }
}