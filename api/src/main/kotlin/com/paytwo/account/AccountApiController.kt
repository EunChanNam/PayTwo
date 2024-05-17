package com.paytwo.account

import com.paytwo.account.business.AccountService
import com.paytwo.account.request.AccountCreateRequest
import com.paytwo.support.ApiResponse
import com.paytwo.support.auth.Authorization
import com.paytwo.user.model.AuthUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountApiController(
    private val accountService: AccountService
) {

    @PostMapping("/v1/accounts")
    fun createNewAccount(
        @Authorization authUser: AuthUser,
        @RequestBody request: AccountCreateRequest
    ): ResponseEntity<ApiResponse> {
        val accountId = accountService.createNewAccount(request.bankName, request.accountNumber, authUser.id)
        return ResponseEntity.ok(ApiResponse(accountId))
    }
}
