package com.paytwo.user.implement

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.paytwo.JwtProvider
import com.paytwo.support.error.DomainException
import com.paytwo.user.model.AuthUser
import org.springframework.stereotype.Component

@Component
class TokenProvider(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) {

    fun issueToken(user: AuthUser): String {
        return jwtProvider.createAccessToken(user)
    }

    fun extractUser(token: String): AuthUser {
        val payload = jwtProvider.getPayload(token)
        return objectMapper.readValue<AuthUser>(objectMapper.writeValueAsString(payload))
    }

    fun validateToken(token: String) {
        if (!jwtProvider.isValidToken(token)) {
            throw DomainException("유효하지 않은 토큰입니다", 400)
        }
    }
}
