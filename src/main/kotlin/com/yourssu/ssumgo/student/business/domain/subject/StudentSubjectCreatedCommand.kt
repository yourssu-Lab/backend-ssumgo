package com.yourssu.ssumgo.student.business.domain.subject

data class StudentSubjectCreatedCommand(
    val studentId: Long,
    val subjectId: Long,
    val years: Int,
    val semester: Int
) {
}
