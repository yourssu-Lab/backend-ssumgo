package com.yourssu.ssumgo.student.implement.domain.subject

import com.yourssu.ssumgo.common.implement.domain.common.BaseDomain

class Subject(
    val id: Long? = null,
    val subjectName: String,
    val professorName: String,
    val completion: String,
    val subjectCode: Int,
    val department: String = "글로벌미디어학부",
    var time: Int,
    var credit: Int,
): BaseDomain() {
}