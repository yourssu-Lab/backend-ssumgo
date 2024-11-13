package com.yourssu.ssumgo.common.storage.support.soomsil

import com.yourssu.ssumgo.common.implement.support.soomsil.AuthClient
import com.yourssu.ssumgo.common.implement.support.soomsil.RefreshTokenClientRequest
import com.yourssu.ssumgo.common.implement.support.soomsil.SignInClientRequest
import com.yourssu.ssumgo.common.implement.support.soomsil.TokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "authClient",
    url = "https://api.test.auth.yourssu.com:443",
    configuration = [FeignConfig::class],
)
interface AuthFeignClientImpl: AuthClient {
    @PostMapping("/v2/auth/sign-in")
    override fun signIn(@RequestBody request: SignInClientRequest): TokenResponse

    @PostMapping("/v2/auth/refresh")
    override fun getRefreshToken(@RequestBody request: RefreshTokenClientRequest): TokenResponse
}
