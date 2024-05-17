package com.paytwo.account.repository

import com.paytwo.account.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountEntityRepository : JpaRepository<AccountEntity, Long> {

    fun findByAccountNumber(accountNumber: Long): AccountEntity?
}
