package com.paytwo.user.implement

import com.paytwo.support.error.DomainException
import com.paytwo.user.model.User
import com.paytwo.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
@DisplayName("[AuthManger 테스트]")
class AuthMangerTest {

    @Mock
    private lateinit var tokenProvider: TokenProvider

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var authManger: AuthManger

    @Test
    @DisplayName("[인증에 성공한다]")
    fun authenticate() {
        // given
        val user = User(1L, "name", "username", "password", true)
        given(tokenProvider.issueToken(any())).willReturn("token")
        given(userRepository.findByUsername(anyString())).willReturn(user)

        // when
        val token = authManger.authenticate("username", "password")

        // then
        assertThat(token).isEqualTo("token")
    }

    @Test
    @DisplayName("[아이디가 틀려 인증에 실패한다]")
    fun authenticate2() {
        // given
        given(userRepository.findByUsername(anyString())).willReturn(null)

        // when, then
        assertThatThrownBy { authManger.authenticate("username", "password") }
            .isInstanceOf(DomainException::class.java)
    }

    @Test
    @DisplayName("[비밀번호가 틀려 인증에 실패한다]")
    fun authenticate3() {
        // given
        val user = User(1L, "name", "username", "password", true)
        given(userRepository.findByUsername(anyString())).willReturn(user)

        // when, then
        assertThatThrownBy { authManger.authenticate("username", "password2") }
            .isInstanceOf(DomainException::class.java)
    }
}
