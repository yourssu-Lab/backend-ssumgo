package com.yourssu.ssumgo.common.support.fixture

import com.yourssu.ssumgo.student.business.domain.subject.SubjectCreatedCommand
import com.yourssu.ssumgo.student.implement.domain.subject.Subject

enum class SubjectFixture(
    val subjectName: String,
    val professorName: String,
    val completion: String,
    val subjectCode: Int,
    val department: String,
    var time: Int,
    var credit: Int,
    ) {
    SUBJECT_1(
        subjectName = "과목명",
        professorName = "교수명",
        completion = "이수구분",
        subjectCode = 1,
        department = "학과",
        time = 1,
        credit = 3,
    ),
    SUBJECT_2(
    subjectName = "과목명",
    professorName = "교수명",
    completion = "이수구분",
    subjectCode = 2,
    department = "학과",
    time = 1,
    credit = 3,
    ),
    SUBJECT_3(
        subjectName = "과목명",
        professorName = "교수명",
        completion = "이수구분",
        subjectCode = 3,
        department = "학과",
        time = 1,
        credit = 3,
    );

    fun toDomain(): Subject {
        return Subject(
            subjectName = subjectName,
            professorName = professorName,
            completion = completion,
            subjectCode = subjectCode,
            department = department,
            time = time,
            credit = credit,
        )
    }

    fun toCreatedCommand(): SubjectCreatedCommand {
        return SubjectCreatedCommand(
            subjectName = subjectName,
            professorName = professorName,
            completion = completion,
            subjectCode = subjectCode,
            department = department,
            time = time,
            credit = credit,
        )
    }
}