package com.paytwo

import com.fasterxml.jackson.annotation.JsonProperty
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[JwtProvider 테스트]")
class JwtProviderTest {

    private val jwtProvider = JwtProvider("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 100)

    @Test
    @DisplayName("토큰에서 payload 데이터를 꺼낸다")
    fun getPayload() {
        // given
        val token = jwtProvider.createAccessToken(TestObject(26, "eunchan"))

        // when
        val payload = jwtProvider.getPayload(token)

        // then
        assertThat(payload).isInstanceOf(Map::class.java)
        val result = payload as Map<*, *>
        assertThat(result["age"]).isEqualTo(26)
        assertThat(result["name"]).isEqualTo("eunchan")
    }

    @Test
    @DisplayName("jwt 토큰을 생성한다")
    fun createAccessToken() {
        // when
        val token = jwtProvider.createAccessToken(TestObject(26, "eunchan"))

        // then
        assertThat(token).isNotNull()
    }

    @Test
    @DisplayName("jwt 토큰 검증에 실패한다")
    fun isValidToken1() {
        // given
        val jwtProvider = JwtProvider("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 0)
        val expiredToken = jwtProvider.createAccessToken(TestObject(26, "eunchan"))

        // when
        val result = jwtProvider.isValidToken(expiredToken)

        // then
        assertThat(result).isFalse()
    }

    @Test
    @DisplayName("jwt 토큰 검증에 성공한다")
    fun isValidToken2() {
        // given
        val token = jwtProvider.createAccessToken(TestObject(26, "eunchan"))

        // when
        val result = jwtProvider.isValidToken(token)

        // then
        assertThat(result).isTrue()
    }
}

data class TestObject(
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("name")
    val name: String
)
