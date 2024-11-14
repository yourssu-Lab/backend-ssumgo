package com.yourssu.ssumgo.common.application.domain.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Response<T>(
    val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")),
    val result: T
) {
}