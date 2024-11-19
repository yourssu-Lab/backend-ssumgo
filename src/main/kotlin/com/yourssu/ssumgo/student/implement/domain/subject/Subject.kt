package com.yourssu.ssumgo.student.implement.domain.subject

class Subject(
    val id: Long? = null,
    val subjectName: String,
    val professorName: String,
    val completion: String,
    val subjectCode: Int,
    val department: String,
    var time: Int,
    var credit: Int,
) {
}