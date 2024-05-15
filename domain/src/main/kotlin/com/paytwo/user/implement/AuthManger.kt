package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
import com.paytwo.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class AuthManger(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) {

    fun authenticate(username: String, password: String): String {
        val user = userRepository.findByUsername(username)
        if (user == null || password != user.password) {
            throw DomainException("일치하지 않는 아이디 혹은 비밀번호 입니다.", 400)
        }

        return tokenProvider.issueToken(user.toAuthUser())
    }
}
