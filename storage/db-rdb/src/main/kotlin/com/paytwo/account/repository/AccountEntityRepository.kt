package com.paytwo.account.repository

import com.paytwo.account.entity.AccountEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface AccountEntityRepository : JpaRepository<AccountEntity, Long> {

    fun findByAccountNumber(accountNumber: Long): AccountEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithLockByAccountNumber(accountNumber: Long): AccountEntity?
}
