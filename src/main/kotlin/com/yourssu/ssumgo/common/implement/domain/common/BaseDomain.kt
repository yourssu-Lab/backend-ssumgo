package com.yourssu.ssumgo.common.implement.domain.common

import java.time.LocalDateTime

abstract class BaseDomain(
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null,
) {
}