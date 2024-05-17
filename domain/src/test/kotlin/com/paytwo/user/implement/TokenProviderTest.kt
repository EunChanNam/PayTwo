package com.paytwo.user.implement

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.paytwo.JwtProvider
import com.paytwo.support.error.DomainException
import com.paytwo.user.model.AuthUser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.kotlin.any

@DisplayName("[TokenProvider 테스트]")
class TokenProviderTest {

    private val jwtProvider: JwtProvider = Mockito.mock(JwtProvider::class.java)
    private val tokenProvider: TokenProvider = TokenProvider(
        jwtProvider,
        ObjectMapper().registerModule(KotlinModule.Builder().build())
    )

    @Test
    @DisplayName("[인증 토큰을 발급한다]")
    fun issueToken() {
        // given
        given(jwtProvider.createAccessToken(any())).willReturn("token")

        // when
        val token = tokenProvider.issueToken(AuthUser(1L, "eunchan"))

        // then
        assertThat(token).isEqualTo("token")
    }

    @Test
    @DisplayName("[토큰에서 User 객체를 추출한다]")
    fun extractUser() {
        // given
        val payload = mapOf(Pair("id", 1L), Pair("name", "eunchan"))
        given(jwtProvider.getPayload(anyString())).willReturn(payload)

        // when
        val authUser = tokenProvider.extractUser("token")

        // then
        assertThat(authUser.name).isEqualTo("eunchan")
        assertThat(authUser.id).isEqualTo(1L)
    }

    @Test
    @DisplayName("[인증 토큰 검증에 성공한다]")
    fun validateToken1() {
        // given
        given(jwtProvider.isValidToken(anyString())).willReturn(true)

        // when, then
        assertDoesNotThrow { tokenProvider.validateToken("token") }
    }

    @Test
    @DisplayName("[인증 토큰 검증에 실패한다]")
    fun validateToken2() {
        // given
        given(jwtProvider.isValidToken(anyString())).willReturn(false)

        // when, then
        assertThatThrownBy { tokenProvider.validateToken("token") }
            .isInstanceOf(DomainException::class.java)
    }
}
