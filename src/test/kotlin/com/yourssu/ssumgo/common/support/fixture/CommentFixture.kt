package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.business.domain.comment.CommentCreatedCommand
import com.yourssu.ssumgo.student.implement.domain.comment.Comment
import com.yourssu.ssumgo.student.implement.domain.posts.Posts
import com.yourssu.ssumgo.student.implement.domain.student.Student

enum class CommentFixture(
    val title: String,
    val content: String,
) {
    COMMENT(
        title = "title_comment",
        content = "content_comment"
    );

    fun toDomain(mentor: Student, posts: Posts): Comment {
        return Comment(
            mentor = mentor,
            posts = posts,
            title = title,
            content = content,
        )
    }

    fun toCommentCreatedCommand(mentorId: Long, postsId: Long): CommentCreatedCommand {
        return CommentCreatedCommand(
            mentorId = mentorId,
            postsId = postsId,
            title = title,
            content = content
        )
    }
}