package com.yourssu.ssumgo.student.business.domain.subject

import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectReader
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectWriter
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectReader: SubjectReader,
    private val subjectWriter: SubjectWriter,
) {
    fun saveSubject(command: SubjectCreatedCommand): SubjectResponse {
        return SubjectResponse.from(subjectWriter.save(command.toDomain()))
    }

    fun getSubjectsByStudent(studentId: Long, years: Int, semester: Int): List<SubjectResponse> {
        return subjectReader.getSubjects(studentId, years, semester).map { SubjectResponse.from(it) }
    }

    fun getAllSubjects(): List<SubjectResponse> {
        return subjectReader.getAllSubjects().map { SubjectResponse.from(it) }
    }
}

data class SubjectCreatedCommand(
    val subjectName: String,
    val professorName: String,
    val completion: String,
    val subjectCode: Int,
    val department: String,
    var time: Int,
    var credit: Int,
) {
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
}