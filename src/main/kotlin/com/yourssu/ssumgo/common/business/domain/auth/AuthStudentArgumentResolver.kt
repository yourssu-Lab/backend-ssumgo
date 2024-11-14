package com.yourssu.ssumgo.common.business.domain.auth

import com.yourssu.ssumgo.common.implement.domain.auth.StudentId
import com.yourssu.ssumgo.common.implement.domain.auth.AccessTokenExtractor
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthStudentArgumentResolver(
    private val accessTokenExtractor: AccessTokenExtractor
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(StudentId::class.java) &&
                parameter.parameterType == Long::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val token = request?.getHeader(HttpHeaders.AUTHORIZATION)
        if (token.isNullOrBlank()) {
            throw IllegalArgumentException("Authorization header is missing")
        }

        return accessTokenExtractor.extractStudentId(token)
    }
}
