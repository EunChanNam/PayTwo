package com.paytwo.account.repository

import com.paytwo.account.entity.TransactionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionHistoryEntityRepository : JpaRepository<TransactionHistoryEntity, Long>
