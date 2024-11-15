package com.yourssu.ssumgo.student.storage.domain.student

import com.yourssu.ssumgo.common.storage.domain.common.BaseEntity
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import jakarta.persistence.*

@Table(name = "student")
@Entity
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val yourssuId: String,

    @Column(nullable = true)
    var nickname: String? = null,

    @Column(nullable = false)
    val department: String,

    @Column(nullable = false)
    val studentIdNumber: Int,

    @Embedded
    @Column(nullable = false)
    var profileImageUrls: ProfileImageUrlsEntity,
) : BaseEntity() {

    companion object {
        fun toEntity(student: Student): StudentEntity {
            return StudentEntity(
                id = student.id,
                yourssuId = student.yourssuId,
                nickname = student.nickname,
                department = student.department,
                studentIdNumber = student.studentIdNumber,
                profileImageUrls = ProfileImageUrlsEntity.toEntity(student.profileImageUrls),
            )
        }
    }

    fun updateProfile(student: StudentEntity) {
        this.nickname = student.nickname
        this.profileImageUrls = student.profileImageUrls
    }

    fun toDomain(): Student {
        return Student(
            id = id,
            yourssuId = yourssuId,
            nickname = nickname,
            department = department,
            studentIdNumber = studentIdNumber,
            profileImageUrls = profileImageUrls.toDomain(),
        )
    }
}

@Embeddable
class ProfileImageUrlsEntity(
    @Column(nullable = false)
    var smallUrl: String,

    @Column(nullable = false)
    var midUrl: String,

    @Column(nullable = false)
    var largeUrl: String,
) {

    companion object {
        fun toEntity(profileImageUrls: ProfileImageUrls): ProfileImageUrlsEntity {
            return ProfileImageUrlsEntity(
                smallUrl = profileImageUrls.smallUrl,
                midUrl = profileImageUrls.midUrl,
                largeUrl = profileImageUrls.largeUrl,
            )
        }
    }

    fun toDomain(): ProfileImageUrls {
        return ProfileImageUrls(
            smallUrl = smallUrl,
            midUrl = midUrl,
            largeUrl = largeUrl,
        )
    }
}
