package com.paytwo.account.request

data class AccountCreateRequest(
    val bankName: String,
    val accountNumber: Long
)
