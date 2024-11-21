package com.yourssu.ssumgo.student.implement.domain.evaluation

import com.yourssu.ssumgo.common.application.domain.common.NotFoundException

enum class Rating(
    val score: Int,
) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    companion object {
        fun of(score: Int): Rating {
            try {
                return entries.first { it.score == score }
            } catch (e: NoSuchElementException) {
                throw RatingNotFoundException("Rating not found for score: $score")
            }
        }
    }
}

class RatingNotFoundException(message: String) : NotFoundException(message = message)