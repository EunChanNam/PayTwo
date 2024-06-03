package com.paytwo.account.model

import java.time.LocalDateTime

data class TransferHistory(
    val id: Long? = null,
    val account: Account,
    val dealType: DealType,
    val amount: Int,
    val dealAt: LocalDateTime,

    val partnerName: String,
    val partnerAccount: Account
)
