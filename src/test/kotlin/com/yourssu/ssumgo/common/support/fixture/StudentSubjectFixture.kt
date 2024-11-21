package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.business.domain.subject.StudentSubjectCreatedCommand

enum class StudentSubjectFixture(
    val years: Int,
    val semester: Int,
) {
    SEMESTER_1(
        years = 2024,
        semester = 1,
    ),
    SEMESTER_2(
        years = 2024,
        semester = 2,
    );

    fun toStudentSubjectCreatedCommand(studentId: Long, subjectId: Long): StudentSubjectCreatedCommand {
        return StudentSubjectCreatedCommand(
            studentId = studentId,
            subjectId = subjectId,
            years = years,
            semester = semester
        )
    }
}