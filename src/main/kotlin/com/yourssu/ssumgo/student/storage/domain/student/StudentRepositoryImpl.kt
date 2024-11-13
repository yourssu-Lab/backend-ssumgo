package com.yourssu.ssumgo.student.storage.domain.student

import com.yourssu.ssumgo.student.implement.domain.student.Student
import com.yourssu.ssumgo.student.implement.domain.student.StudentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class StudentRepositoryImpl(
    private val studentJpaRepository: StudentJpaRepository
) : StudentRepository {
    override fun saveOrUpdate(request: Student): Student {
        val studentEntity: StudentEntity = StudentEntity.toEntity(request)
        val savedStudent = studentJpaRepository.getByYourssuId(request.yourssuId) ?: let {
            return studentJpaRepository.save(studentEntity).toDomain()
        }
        savedStudent.updateProfile(studentEntity)
        return savedStudent.toDomain()
    }

    override fun get(id: Long): Student {
        val student: StudentEntity = studentJpaRepository.get(id) ?: throw IllegalArgumentException("Student not found")
        return student.toDomain()
    }
}

interface StudentJpaRepository : JpaRepository<StudentEntity, Long> {
    fun get(id: Long): StudentEntity?

    fun getByYourssuId(yourssuId: String): StudentEntity?
}
