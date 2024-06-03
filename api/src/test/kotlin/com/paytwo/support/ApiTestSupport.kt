package com.paytwo.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.paytwo.PaytwoApiApplication
import com.paytwo.user.request.LoginRequest
import com.paytwo.user.request.SignUpRequest
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest(classes = [PaytwoApiApplication::class])
@AutoConfigureMockMvc
abstract class ApiTestSupport : TestContainerSupport() {

    @Autowired
    protected var mockMvc: MockMvc? = null

    @Autowired
    protected var objectMapper: ObjectMapper? = null

    protected var accessToken: String? = null

    protected fun toJson(target: Any?): String {
        return objectMapper!!.writeValueAsString(target)
    }

    protected fun login(username: String, password: String): String {
        val loginRequest = LoginRequest(username, password)
        val loginResult = mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(loginRequest))
        )?.andReturn()

        val stringLoginResponse = loginResult?.response?.contentAsString
        val authResponse: ApiResponse? = objectMapper?.readValue(stringLoginResponse, ApiResponse::class.java)

        return authResponse?.data as String
    }

    @PostConstruct
    fun setUpUser() {
        mockMvc ?: throw IllegalArgumentException("mockMvc is null")
        // 캐싱해서 단 한번만 호출
        if (accessToken != null) {
            return
        }

        val signUpRequest = SignUpRequest("name", "username", "password123@", true)
        mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest))
        )

        val loginRequest = LoginRequest("username", "password123@")
        val loginResult = mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(loginRequest))
        )?.andReturn()

        val stringLoginResponse = loginResult?.response?.contentAsString
        val authResponse: ApiResponse? = objectMapper?.readValue(stringLoginResponse, ApiResponse::class.java)

        accessToken = authResponse?.data as String
    }
}
