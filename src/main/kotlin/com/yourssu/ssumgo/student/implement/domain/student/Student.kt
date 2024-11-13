package com.yourssu.ssumgo.student.implement.domain.student

import java.time.LocalDateTime

class Student(
    val id: Long? = null,
    val yourssuId: String,
    val nickname: String? = null,
    val department: String = "글로벌미디어학부",
    val studentIdNumber: Int = 24,
    val profileImageUrls: ProfileImageUrls,
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null,
)

class ProfileImageUrls(
    val smallUrl: String,
    val midUrl: String,
    val largeUrl: String,
)
