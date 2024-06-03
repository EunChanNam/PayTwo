package com.paytwo.account.model

data class Account(
    val id: Long? = null,
    val bankName: String,
    val accountNumber: Long,
    var balance: Int,
    val userId: Long
)
