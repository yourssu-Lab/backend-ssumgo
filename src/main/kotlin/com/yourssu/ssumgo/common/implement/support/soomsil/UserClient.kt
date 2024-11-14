package com.yourssu.ssumgo.common.implement.support.soomsil

import com.yourssu.ssumgo.student.implement.domain.student.ProfileImageUrls
import com.yourssu.ssumgo.student.implement.domain.student.Student
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@Component
interface UserClient {
    @GetMapping
    fun getUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) token: String): UserResponse
}

data class UserResponse(
    val email: String,
    val nickName: String,
    val profileImage: ProfileImageUrlsResponse,
) {
    fun toDomain(): Student {
        return Student(
            yourssuId = email.replace("@yourssu.com", ""),
            nickname = nickName,
            profileImageUrls = profileImage.toDomain()
        )
    }
}

data class ProfileImageUrlsResponse(
    val smallUrl: String,
    val midUrl: String,
    val largeUrl: String,
) {
    fun toDomain(): ProfileImageUrls {
        return ProfileImageUrls(
            smallUrl = smallUrl,
            midUrl = midUrl,
            largeUrl = largeUrl)
    }
}
