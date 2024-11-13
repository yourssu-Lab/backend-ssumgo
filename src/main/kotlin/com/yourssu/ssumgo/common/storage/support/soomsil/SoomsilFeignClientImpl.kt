package com.yourssu.ssumgo.common.storage.support.soomsil

import com.yourssu.ssumgo.common.implement.support.soomsil.UserClient
import com.yourssu.ssumgo.common.implement.support.soomsil.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "soomsilClient",
    url = "https://test.ground.yourssu.com/ground",
    configuration = [FeignConfig::class],
)
interface SoomsilFeignClientImpl: UserClient {
    @GetMapping("/v2/users")
    override fun getUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) token: String): UserResponse
}
