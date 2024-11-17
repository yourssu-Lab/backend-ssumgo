package com.yourssu.ssumgo.student.business.domain.comment

import com.yourssu.ssumgo.student.business.domain.posts.PostsResponse
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.student.Student

data class CommentResponse(
    val commentId: Long,
    val post: PostsResponse,
    val mentor: MentorResponse,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                commentId = comment.id!!,
                post = PostsResponse.from(comment.posts),
                mentor = MentorResponse.from(comment.mentor),
                title = comment.title,
                content = comment.content,
            )
        }
    }
}

data class MentorResponse(
    val mentorId: Long,
    val mentorName: String,
    val mentorDepartment: String,
    val mentorStudentIdNumber: Int,
) {
    companion object {
        fun from(mentor: Student): MentorResponse {
            return MentorResponse(
                mentorId = mentor.id!!,
                mentorName = mentor.nickname!!,
                mentorDepartment = mentor.department,
                mentorStudentIdNumber = mentor.studentIdNumber,
            )
        }
    }
}
