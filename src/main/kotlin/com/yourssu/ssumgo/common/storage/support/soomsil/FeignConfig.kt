package com.yourssu.ssumgo.common.storage.support.soomsil

import feign.Logger
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class FeignConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return ErrorDecoder { methodKey, response ->
            when (response.status()) {
                400 -> BadRequestException("Bad Request")
                401 -> UnauthorizedException("Unauthorized")
                404 -> NotFoundException("Not Found")
                else -> Exception("External API error: ${response.status()}")
            }
        }
    }
}

class BadRequestException(message: String) : RuntimeException(message)
class UnauthorizedException(message: String) : RuntimeException(message)
class NotFoundException(message: String) : RuntimeException(message)
