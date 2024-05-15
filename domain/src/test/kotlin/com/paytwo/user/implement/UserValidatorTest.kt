package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
import com.paytwo.user.model.User
import com.paytwo.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
@DisplayName("[UserValidator 테스트]")
class UserValidatorTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userValidator: UserValidator

    @Test
    @DisplayName("[회원 중복검증에 성공한다]")
    fun validateUsernameDuplication() {
        // given
        given { userRepository.findByUsername(any()) }.willReturn(null)

        // when, then
        assertDoesNotThrow { userValidator.validateUsernameDuplication("username") }
    }

    @Test
    @DisplayName("[중복된 아이디로 회원 중복검증에 성공한다]")
    fun validateUsernameDuplication2() {
        // given
        val user = User(null, "name", "username", "password", true)
        given { userRepository.findByUsername(any()) }.willReturn(user)

        // when, then
        assertThatThrownBy { userValidator.validateUsernameDuplication("username") }
            .isInstanceOf(DomainException::class.java)
    }
}
