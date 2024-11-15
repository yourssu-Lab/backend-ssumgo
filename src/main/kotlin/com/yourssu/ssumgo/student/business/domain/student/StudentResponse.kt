package com.yourssu.ssumgo.student.business.domain.student

import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student

data class StudentResponse(
    val studentId: Long,
    val yourssuId: String,
    val nickname: String? = null,
    val department: String,
    val studentIdNumber: Int,
    val profileImageUrls: ProfileImageUrlsResponse,
) {
    companion object {
        fun from(student: Student): StudentResponse {
            return StudentResponse(
                studentId = student.id!!,
                yourssuId = student.yourssuId,
                nickname = student.nickname!!,
                department = student.department,
                studentIdNumber = student.studentIdNumber,
                profileImageUrls = ProfileImageUrlsResponse.from(student.profileImageUrls),
            )
        }
    }
}

data class ProfileImageUrlsResponse(
    val smallUrl: String,
    val midUrl: String,
    val largeUrl: String,
) {
    companion object {
        fun from(profileImageUrls: ProfileImageUrls): ProfileImageUrlsResponse {
            return ProfileImageUrlsResponse(
                smallUrl = profileImageUrls.smallUrl,
                midUrl = profileImageUrls.midUrl,
                largeUrl = profileImageUrls.largeUrl
            )
        }
    }
}