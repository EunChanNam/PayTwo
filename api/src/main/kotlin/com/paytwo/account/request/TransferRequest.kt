package com.paytwo.account.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TransferRequest(
    val senderAccountNumber: Long,
    val receiverBankName: String,
    val receiverAccountNumber: Long,
    val amount: Int,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    val dealAt: LocalDateTime
)
