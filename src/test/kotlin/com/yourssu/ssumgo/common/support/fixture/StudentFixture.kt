package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.common.support.fixture.ProfileImageUrlsFixture.PROFILE_IMAGE
import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student

enum class StudentFixture(
    val yourssuId: String,
    val nickname: String,
    val department: String,
    val studentIdNumber: Int,
    val profileImageUrls: ProfileImageUrls,
) {
    STUDENT_LEO(
        yourssuId = "leo",
        nickname = "레오",
        department = "글로벌미디어학부",
        studentIdNumber = 24,
        profileImageUrls = PROFILE_IMAGE.toProfileImageUrls()
    ),
    STUDENT_MAI(
        yourssuId = "mai",
        nickname = "마이",
        department = "글로벌미디어학부",
        studentIdNumber = 24,
        profileImageUrls = PROFILE_IMAGE.toProfileImageUrls()
    ),
    STUDENT_TOM(
        yourssuId = "tom",
        nickname = "톰",
        department = "글로벌미디어학부",
        studentIdNumber = 24,
        profileImageUrls = PROFILE_IMAGE.toProfileImageUrls()
    );

    fun toDomain(nickname: String = this.nickname): Student {
        return Student(
            yourssuId = yourssuId,
            nickname = nickname,
            department = department,
            studentIdNumber = studentIdNumber,
            profileImageUrls = profileImageUrls,
        )
    }
}

enum class ProfileImageUrlsFixture(
    val smallUrl: String,
    val midUrl: String,
    val largeUrl: String,
) {
    PROFILE_IMAGE(
        smallUrl = "https://yourssu.s3.com/profile/small/image.jpg",
        midUrl = "https://yourssu.s3.com/profile/small/image.jpg",
        largeUrl = "https://yourssu.s3.com/profile/small/image.jpg",
    );

    fun toProfileImageUrls(): ProfileImageUrls {
        return ProfileImageUrls(
            smallUrl = smallUrl,
            midUrl = midUrl,
            largeUrl = largeUrl,
        )
    }
}