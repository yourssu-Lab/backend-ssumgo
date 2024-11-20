package com.yourssu.ssumgo.student.implement.domain.subject

import com.yourssu.ssumgo.student.implement.domain.student.Student


interface StudentSubjectRepository {
    fun save(student: Student, subject: Subject, years: Int, semester: Int): Subject
    fun existsStudentSubject(studentId: Long, subjectId: Long, years: Int, semester: Int): Boolean
    fun getSubjects(studentId: Long, years: Int, semester: Int): List<Subject>
}