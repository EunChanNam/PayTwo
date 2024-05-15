package com.paytwo.user

import com.paytwo.support.ApiTestSupport
import com.paytwo.user.request.LoginRequest
import com.paytwo.user.request.SignUpRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[User API 테스트]")
class UserApiControllerTest : ApiTestSupport() {

    @Test
    @DisplayName("[회원가입 API]")
    fun signUp() {
        // given
        val signUpRequest = SignUpRequest("eunchan", "username1", "password", true)

        // when
        val actions = mockMvc?.perform {
            MockMvcRequestBuilders
                .post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest))
                .buildRequest(it)
        }

        // then
        actions?.andExpect(status().isOk)
    }

    @Test
    @DisplayName("[로그인 API]")
    fun login() {
        // given
        val loginRequest = LoginRequest("username1", "password")

        // when
        val actions = mockMvc?.perform {
            MockMvcRequestBuilders
                .post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(loginRequest))
                .buildRequest(it)
        }

        // then
        actions?.andExpect(status().isOk)
    }
}
