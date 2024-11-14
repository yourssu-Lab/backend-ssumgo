package com.yourssu.ssumgo.student.implement.domain.student

interface StudentRepository {
    fun saveOrUpdate(student: Student): Student

    fun get(id: Long): Student
}
