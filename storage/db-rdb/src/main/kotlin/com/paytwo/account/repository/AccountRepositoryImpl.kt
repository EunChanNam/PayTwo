package com.paytwo.account.repository

import com.paytwo.account.entity.AccountEntity
import com.paytwo.account.model.Account
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val accountEntityRepository: AccountEntityRepository
) : AccountRepository {

    override fun save(account: Account): Account {
        val accountEntity = AccountEntity(
            bankName = account.bankName,
            accountNumber = account.accountNumber,
            balance = account.balance,
            userId = account.userId
        )
        accountEntityRepository.save(accountEntity)
        return accountEntity.toDomain()
    }

    override fun findByAccountNumber(accountNumber: Long): Account? {
        return accountEntityRepository.findByAccountNumber(accountNumber)?.toDomain()
    }

    override fun findByAccountNumberWithLock(accountNumber: Long): Account? {
        return accountEntityRepository.findWithLockByAccountNumber(accountNumber)?.toDomain()
    }

    override fun update(account: Account) {
        val accountEntity = AccountEntity.from(account)
        accountEntityRepository.save(accountEntity)
    }

    override fun findAll(): List<Account> {
        return accountEntityRepository.findAll()
            .map { it.toDomain() }
    }
}
