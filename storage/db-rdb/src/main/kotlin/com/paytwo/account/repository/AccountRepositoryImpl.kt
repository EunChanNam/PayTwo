package com.paytwo.account.repository

import com.paytwo.account.entity.AccountEntity
import com.paytwo.account.model.Account
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val accountEntityRepository: AccountEntityRepository
) : AccountRepository {

    override fun save(account: Account): Account {
        val accountEntity = AccountEntity(account.bankName, account.accountNumber, account.balance, account.userId)
        accountEntityRepository.save(accountEntity)
        return accountEntity.toDomain()
    }

    override fun findByAccountNumber(accountNumber: Long): Account? {
        return accountEntityRepository.findByAccountNumber(accountNumber)?.toDomain()
    }
}
