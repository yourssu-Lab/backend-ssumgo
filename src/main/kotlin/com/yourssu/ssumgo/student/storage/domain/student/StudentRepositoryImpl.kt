package com.yourssu.ssumgo.student.storage.domain.student

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class StudentRepositoryImpl(
    private val studentJpaRepository: StudentJpaRepository
) : StudentRepository {
    override fun saveOrUpdate(student: Student): Student {
        val studentEntity: StudentEntity = StudentEntity.toEntity(student)
        val savedStudent = studentJpaRepository.getByYourssuId(student.yourssuId) ?: let {
            return studentJpaRepository.save(studentEntity).toDomain()
        }
        savedStudent.updateProfile(studentEntity)
        return savedStudent.toDomain()
    }

    override fun get(id: Long): Student {
        val student: StudentEntity = studentJpaRepository.get(id) ?: throw StudentNotFoundException()
        return student.toDomain()
    }
}

interface StudentJpaRepository : JpaRepository<StudentEntity, Long> {
    @Query("select s from StudentEntity s where s.id = :id")
    fun get(id: Long): StudentEntity?

    fun getByYourssuId(yourssuId: String): StudentEntity?
}

class StudentNotFoundException : NotFoundException(message = "존재하지 않는 학생입니다.")