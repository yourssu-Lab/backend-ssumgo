package com.yourssu.ssumgo.common.application.support.auth

import com.yourssu.ssumgo.common.business.domain.auth.AuthStudentArgumentResolver
import com.yourssu.ssumgo.common.implement.domain.auth.AccessTokenExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    @Autowired
    private lateinit var accessTokenExtractor: AccessTokenExtractor

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(AuthStudentArgumentResolver(accessTokenExtractor))
    }
}