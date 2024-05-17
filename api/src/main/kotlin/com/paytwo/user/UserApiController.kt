package com.paytwo.user

import com.paytwo.support.ApiResponse
import com.paytwo.user.business.UserService
import com.paytwo.user.request.LoginRequest
import com.paytwo.user.request.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApiController(
    private val userService: UserService
) {

    @PostMapping("/v1/users")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<ApiResponse> {
        val userId = userService.signUp(request.toUser())
        return ResponseEntity.ok(ApiResponse(userId))
    }

    @PostMapping("/v1/users/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<ApiResponse> {
        val token = userService.login(request.username, request.password)
        return ResponseEntity.ok(ApiResponse(token))
    }
}
