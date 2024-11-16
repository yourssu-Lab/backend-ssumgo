package com.yourssu.ssumgo.student.implement.domain.posts

import org.springframework.data.domain.Sort

enum class SortBy(
    val direction: Sort.Direction,
    val property: String,
) {
    LATEST(Sort.Direction.DESC, "latest"),
    EARLIEST(Sort.Direction.ASC, "earliest");

    companion object {
        fun of(property: String): SortBy {
            return entries.find { it.property == property } ?: throw IllegalArgumentException("Invalid property: $property")
        }
    }
}