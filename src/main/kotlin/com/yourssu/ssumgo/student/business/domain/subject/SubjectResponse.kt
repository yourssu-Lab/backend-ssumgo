package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.Subject

data class SubjectResponse(
    val subjectId: Long,
    val subjectName: String,
    val professorName: String,
    val completion: String,
    val subjectCode: Int,
    val department: String,
    val time: Int,
    val credit: Int,
){
    companion object {
        fun from(subject: Subject): SubjectResponse {
            return SubjectResponse(
                subjectId = subject.id!!,
                subjectName = subject.subjectName,
                professorName = subject.professorName,
                completion = subject.completion,
                subjectCode = subject.subjectCode,
                department = subject.department,
                time = subject.time,
                credit = subject.credit,
            )
        }
    }
}
