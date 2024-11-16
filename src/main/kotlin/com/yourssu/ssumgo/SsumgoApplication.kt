package com.yourssu.ssumgo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
class SsumgoApplication

fun main(args: Array<String>) {
    runApplication<SsumgoApplication>(*args)
}
