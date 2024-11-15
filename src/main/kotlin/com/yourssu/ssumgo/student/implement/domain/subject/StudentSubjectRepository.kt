package com.yourssu.ssumgo.student.implement.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.Student


interface StudentSubjectRepository {
    fun getSubjects(studentId: Long, years: Int, semester: Int): List<Subject>
    fun save(student: Student, subject: Subject, years: Int, semester: Int): Long
}