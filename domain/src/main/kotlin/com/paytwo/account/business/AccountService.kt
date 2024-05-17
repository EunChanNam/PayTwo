package com.paytwo.account.business

import com.paytwo.account.implement.AccountManager
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountManager: AccountManager
) {

    fun createNewAccount(bankName: String, accountNumber: Long, userId: Long): Long {
        return accountManager.createAccount(bankName, accountNumber, userId).id!!
    }
}
