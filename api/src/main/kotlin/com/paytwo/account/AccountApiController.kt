package com.paytwo.account

import com.paytwo.account.business.AccountService
import com.paytwo.account.business.TransferService
import com.paytwo.account.request.AccountCreateRequest
import com.paytwo.account.request.TransferRequest
import com.paytwo.support.ApiResponse
import com.paytwo.support.auth.Authorization
import com.paytwo.user.model.AuthUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountApiController(
    private val accountService: AccountService,
    private val transferService: TransferService
) {

    @PostMapping("/v1/accounts")
    fun createNewAccount(
        @Authorization authUser: AuthUser,
        @RequestBody request: AccountCreateRequest
    ): ResponseEntity<ApiResponse> {
        val accountId = accountService.createNewAccount(request.bankName, request.accountNumber, authUser.id)
        return ResponseEntity.ok(ApiResponse(accountId))
    }

    @PostMapping("/v1/accounts/transfer")
    fun sendMoney(
        @Authorization authUser: AuthUser,
        @RequestBody request: TransferRequest
    ): ResponseEntity<ApiResponse> {
        transferService.sendMoney(
            request.senderAccountNumber,
            request.receiverAccountNumber,
            authUser.id,
            authUser.name,
            request.amount,
            request.dealAt,
            request.receiverBankName
        )

        return ResponseEntity.ok(ApiResponse())
    }

    @PostMapping("/v1/accounts/decrease")
    fun decreaseMoney(
        @RequestParam accountNumber: Long,
        @RequestParam amount: Int
    ): ResponseEntity<ApiResponse> {
        transferService.decreaseMoney(accountNumber, 10000)
        return ResponseEntity.ok(ApiResponse())
    }
}
