package com.paytwo.user.repository

import com.paytwo.user.model.User

interface UserRepository {

    fun save(user: User): User

    fun findByUsername(username: String): User?
}
