package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.Subject

data class SubjectResponse(
    private val subjectId: Long,
    private val subjectName: String,
    private val professorName: String,
    private val completion: String,
    private val subjectCode: Int,
    private val department: String,
    private val time: Int,
    private val credit: Int,
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
