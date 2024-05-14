package com.paytwo.user.model

data class User(
    val id: Long?,
    val name: String,
    val username: String,
    val password: String,
    val notificationEnabled: Boolean
) {
    fun toAuthUser(): AuthUser {
        return AuthUser(id!!, name)
    }
}
