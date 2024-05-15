package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
import com.paytwo.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository
) {

    fun validateUsernameDuplication(username: String) {
        if (userRepository.findByUsername(username) != null) {
            throw DomainException("이미 존재하는 username 입니다", 400)
        }
    }
}
