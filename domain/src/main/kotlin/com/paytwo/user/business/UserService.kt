package com.paytwo.user.business

import com.paytwo.user.implement.AuthManger
import com.paytwo.user.implement.UserManager
import com.paytwo.user.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userManager: UserManager,
    private val authManger: AuthManger
) {

    fun signUp(user: User): Long {
        return userManager.createUser(user)
    }

    fun login(username: String, password: String): String {
        return authManger.authenticate(username, password)
    }
}
