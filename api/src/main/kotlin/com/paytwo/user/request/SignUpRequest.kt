package com.paytwo.user.request

import com.paytwo.user.model.User

data class SignUpRequest(
    val name: String,
    val username: String,
    val password: String,
    val notificationEnabled: Boolean
) {
    fun toUser(): User {
        return User(
            null,
            name,
            username,
            password,
            notificationEnabled
        )
    }
}
