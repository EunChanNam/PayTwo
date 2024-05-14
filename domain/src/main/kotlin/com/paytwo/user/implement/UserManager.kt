package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
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

    fun getByUsernameAndPassword(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
            ?: throw DomainException("일치하지 않는 아이디 혹은 비밀번호 입니다.", 400)
        if (user.password != password) {
            throw DomainException("일치하지 않는 아이디 혹은 비밀번호 입니다.", 400)
        }
        return user
    }
}
