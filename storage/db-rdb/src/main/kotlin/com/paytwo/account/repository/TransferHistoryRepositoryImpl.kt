package com.paytwo.account.repository

import com.paytwo.account.entity.TransactionHistoryEntity
import com.paytwo.account.model.TransferHistory
import org.springframework.stereotype.Repository

@Repository
class TransferHistoryRepositoryImpl(
    private val transactionHistoryEntityRepository: TransactionHistoryEntityRepository
) : TransferHistoryRepository {

    override fun save(transferHistory: TransferHistory): TransferHistory {
        val transactionHistoryEntity = TransactionHistoryEntity.from(transferHistory)
        transactionHistoryEntityRepository.save(transactionHistoryEntity)
        return transactionHistoryEntity.toTransferHistory()
    }
}
