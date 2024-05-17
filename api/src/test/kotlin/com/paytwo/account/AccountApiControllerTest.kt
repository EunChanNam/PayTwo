package com.paytwo.account

import com.paytwo.account.request.AccountCreateRequest
import com.paytwo.support.ApiTestSupport
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("[Account API 테스트]")
class AccountApiControllerTest : ApiTestSupport() {

    @Test
    @DisplayName("계좌 생성 API")
    fun createNewAccount() {
        // given
        val request = AccountCreateRequest("bankName", 123123)

        // when
        val actions = mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/accounts")
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
            )!!

        // then
        actions.andExpectAll(
            status().isOk,
            jsonPath("$.data").isNumber
        )
    }
}
