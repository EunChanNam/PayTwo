package com.paytwo.account.implement

import com.paytwo.account.model.Account
import com.paytwo.account.repository.AccountRepository
import com.paytwo.support.error.DomainException
import org.springframework.stereotype.Component

@Component
class AccountManager(
    private val accountRepository: AccountRepository
) {

    fun createAccount(bankName: String, accountNumber: Long, userId: Long): Account {
        accountRepository.findByAccountNumber(accountNumber)
            ?.also { throw DomainException("이미 등록된 계좌입니다", 400) }

        val account = Account(
            bankName = bankName,
            accountNumber = accountNumber,
            balance = 0,
            userId = userId
        )

        return accountRepository.save(account)
    }
}
