package com.yourssu.ssumgo.student.implement.domain.posts

import com.yourssu.ssumgo.common.application.domain.common.BadRequestException
import org.springframework.data.domain.Sort

enum class SortBy(
    val direction: Sort.Direction,
    val property: String,
) {
    LATEST(Sort.Direction.DESC, "latest"),
    EARLIEST(Sort.Direction.ASC, "earliest");

    companion object {
        fun of(property: String): SortBy {
            return entries.find { it.property == property } ?: throw InvalidSortByException("Invalid property: $property")
        }
    }
}

class InvalidSortByException(message: String) : BadRequestException(message = message)