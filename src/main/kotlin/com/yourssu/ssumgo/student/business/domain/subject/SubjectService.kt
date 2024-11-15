package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.SubjectReader
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectReader: SubjectReader
) {
    fun getSubjectsByStudent(studentId: Long, years: Int, semester: Int): List<SubjectResponse> {
        return subjectReader.getSubjects(studentId, years, semester).map { SubjectResponse.from(it) }
    }

    fun getAllSubjects(): List<SubjectResponse> {
        return subjectReader.getAllSubjects().map { SubjectResponse.from(it) }
    }
}