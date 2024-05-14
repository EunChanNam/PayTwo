package com.paytwo.user.implement

import com.paytwo.JwtProvider
import com.paytwo.support.error.DomainException
import com.paytwo.user.model.AuthUser
import org.springframework.stereotype.Component

@Component
class TokenProvider(
    private val jwtProvider: JwtProvider
) {

    fun issueToken(user: AuthUser): String {
        return jwtProvider.createAccessToken(user)
    }

    fun extractUser(token: String): AuthUser {
        val claim = jwtProvider.getClaim(token)
        if (claim is AuthUser) {
            return claim
        }
        throw DomainException("잘못된 인증 토큰의 payload 입니다.", 500)
    }

    fun validateToken(token: String) {
        if (!jwtProvider.isValidToken(token)) {
            throw DomainException("유효하지 않은 토큰입니다", 400)
        }
    }
}
