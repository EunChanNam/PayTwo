package com.paytwo.account.implement

import com.paytwo.account.model.Account
import com.paytwo.account.model.DealType
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

    fun updateBalance(account: Account, amount: Int, dealType: DealType) {
        val dealAmount = dealType.dealAmount(amount)

        if (account.balance + dealAmount < 0) {
            throw DomainException("계좌에 출금할 잔액이 부족합니다", 400)
        }

        account.balance += dealAmount
        accountRepository.update(account)
    }
}
