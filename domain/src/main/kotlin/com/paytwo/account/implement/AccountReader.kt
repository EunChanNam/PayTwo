package com.paytwo.account.implement

import com.paytwo.account.model.Account
import com.paytwo.account.repository.AccountRepository
import com.paytwo.support.error.DomainException
import org.springframework.stereotype.Component

@Component
class AccountReader(
    private val accountRepository: AccountRepository
) {

    fun readByAccountNumber(accountNumber: Long): Account {
        return accountRepository.findByAccountNumber(accountNumber)
            ?: throw DomainException("존재하지 않는 계좌입니다", 400)
    }

    fun readByAccountNumberWithLock(accountNumber: Long): Account {
        return accountRepository.findByAccountNumberWithLock(accountNumber)
            ?: throw DomainException("존재하지 않는 계좌입니다", 400)
    }
}
