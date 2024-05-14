package com.paytwo.user.implement

import org.springframework.stereotype.Component

@Component
class AuthManger(
    private val tokenProvider: TokenProvider,
    private val userManager: UserManager
) {

    fun login(username: String, password: String): String {
        val user = userManager.getByUsernameAndPassword(username, password)
        return tokenProvider.issueToken(user.toAuthUser())
    }
}
