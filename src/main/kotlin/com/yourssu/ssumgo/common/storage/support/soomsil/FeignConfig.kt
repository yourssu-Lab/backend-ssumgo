package com.yourssu.ssumgo.common.storage.support.soomsil

import feign.Logger
import org.springframework.context.annotation.Bean

class FeignConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL
}
