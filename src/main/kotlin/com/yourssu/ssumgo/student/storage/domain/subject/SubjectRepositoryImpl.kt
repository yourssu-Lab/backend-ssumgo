package com.yourssu.ssumgo.student.storage.domain.subject

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException
import com.yourssu.ssumgo.student.implement.domain.subject.Subject
import com.yourssu.ssumgo.student.implement.domain.subject.SubjectRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
class SubjectRepositoryImpl(
    private val subjectJpaRepository: SubjectJpaRepository
) : SubjectRepository {
    override fun save(subject: Subject): Subject {
        return subjectJpaRepository.save(SubjectEntity.toEntity(subject)).toDomain()
    }

    override fun getAllSubjects(): List<Subject> {
        return subjectJpaRepository.findAll().map { it.toDomain() }
    }

    override fun get(subjectId: Long): Subject {
        return subjectJpaRepository.get(subjectId)?.toDomain()
            ?: throw SubjectNotFoundException()
    }
}

interface SubjectJpaRepository : JpaRepository<SubjectEntity, Long> {
    @Query("select s from SubjectEntity s where s.id = :id")
    fun get(id: Long): SubjectEntity?
}

class SubjectNotFoundException : NotFoundException(message = "해당하는 과목이 없습니다.")
