package com.yourssu.ssumgo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SsumgoApplication

fun main(args: Array<String>) {
    runApplication<SsumgoApplication>(*args)
}
