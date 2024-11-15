package com.yourssu.ssumgo.student.implement.domain.subject

interface SubjectRepository {
    fun save(subject: Subject): Subject
    fun getAllSubjects(): List<Subject>
}