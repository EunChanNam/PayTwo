package com.paytwo.account.model

enum class DealType(
    val dealAmount: (amount: Int) -> Int
) {
    DEPOSIT({ -it }),
    REMITTANCE({ it }),
    PAYMENT({ -it })
    ;
}
