package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
import com.paytwo.user.model.User
import com.paytwo.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun readById(userId: Long): User {
        return userRepository.findById(userId)
            ?: throw DomainException("존재하지 않는 회원입니다", 400)
    }
}
