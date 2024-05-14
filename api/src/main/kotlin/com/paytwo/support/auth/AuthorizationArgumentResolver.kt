package com.paytwo.support.auth

import com.paytwo.user.implement.TokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthorizationArgumentResolver(
    private val tokenProvider: TokenProvider
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(Authorization::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val httpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw IllegalArgumentException("요청 오류입니다")

        val token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        tokenProvider.validateToken(token)
        return tokenProvider.extractUser(token)
    }
}
