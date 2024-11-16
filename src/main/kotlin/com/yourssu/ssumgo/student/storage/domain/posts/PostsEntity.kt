package com.yourssu.ssumgo.student.storage.domain.posts

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.storage.domain.student.StudentEntity
import com.yourssu.ssumgo.student.storage.domain.subject.SubjectEntity
import jakarta.persistence.*

@Entity
@Table(name = "posts")
class PostsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    val mentee: StudentEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    val subject: SubjectEntity,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    ) : BaseEntity() {
    companion object {
        fun toEntity(posts: Posts): PostsEntity {
            return PostsEntity(
                id = posts.id,
                mentee = StudentEntity.toEntity(posts.mentee),
                subject = SubjectEntity.toEntity(posts.subject),
                title = posts.title,
                content = posts.content,
            )
        }
    }

    fun toDomain(): Posts {
        return Posts(
            id = id,
            mentee = mentee.toDomain(),
            subject = subject.toDomain(),
            title = title,
            content = content,
        )
    }
}