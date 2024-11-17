package com.yourssu.ssumgo.student.business.domain.posts

import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student

data class PostsResponse(
    val postId: Long,
    val mentee: MenteeResponse,
    val subjectId: Long,
    val title: String,
    val content: String
) {
    companion object {
        fun from(posts: Posts): PostsResponse {
            return PostsResponse(
                postId = posts.id!!,
                mentee = MenteeResponse.from(posts.mentee),
                subjectId = posts.subject.id!!,
                title = posts.title,
                content = posts.content
            )
        }
    }
}

data class MenteeResponse(
    val menteeId: Long,
    val menteeName: String,
    val menteeDepartment: String,
    val menteeStudentIdNumber: Int
) {
    companion object {
        fun from(mentee: Student): MenteeResponse {
            return MenteeResponse(
                menteeId = mentee.id!!,
                menteeName = mentee.nickname!!,
                menteeDepartment = mentee.department,
                menteeStudentIdNumber = mentee.studentIdNumber
            )
        }
    }
}
