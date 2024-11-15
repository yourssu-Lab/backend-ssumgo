package com.yourssu.ssumgo.student.implement.domain.student

class Student(
    val id: Long? = null,
    val yourssuId: String,
    val nickname: String? = null,
    val department: String = "글로벌미디어학부",
    val studentIdNumber: Int = 24,
    val profileImageUrls: ProfileImageUrls,
)

class ProfileImageUrls(
    val smallUrl: String,
    val midUrl: String,
    val largeUrl: String,
)
