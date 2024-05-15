package com.paytwo.user.implement

import com.paytwo.user.model.User
import com.paytwo.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserManager(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {

    fun createUser(user: User): Long {
        userValidator.validateUsernameDuplication(user.username)
        return userRepository.save(user).id!!
    }
}
