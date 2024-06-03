package com.paytwo.account.repository

import com.paytwo.account.model.Account

interface AccountRepository {

    fun save(account: Account): Account

    fun findByAccountNumber(accountNumber: Long): Account?

    fun findByAccountNumberWithLock(accountNumber: Long): Account?

    fun update(account: Account)

    fun findAll(): List<Account>
}
