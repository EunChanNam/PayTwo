package com.paytwo.account.implement

import com.paytwo.account.model.Account
import com.paytwo.account.model.DealType
import com.paytwo.account.model.TransferHistory
import com.paytwo.account.repository.TransferHistoryRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TransferManager(
    private val accountManager: AccountManager,
    private val transferHistoryRepository: TransferHistoryRepository
) {

    fun deposit(
        senderAccount: Account,
        senderName: String,
        amount: Int,
        dealAt: LocalDateTime,
        receiverName: String,
        receiverAccount: Account
    ) {
        // 각 계좌 잔액 업데이트
        accountManager.updateBalance(senderAccount, amount, DealType.DEPOSIT)
        accountManager.updateBalance(receiverAccount, amount, DealType.REMITTANCE)

        // 거래내역 생성
        val transferHistory = TransferHistory(
            account = senderAccount,
            dealType = DealType.DEPOSIT,
            dealAt = dealAt,
            amount = DealType.DEPOSIT.dealAmount(amount),
            partnerName = receiverName,
            partnerAccount = receiverAccount
        )
        val partnerTransferHistory = TransferHistory(
            account = receiverAccount,
            dealType = DealType.REMITTANCE,
            dealAt = dealAt,
            amount = DealType.REMITTANCE.dealAmount(amount),
            partnerName = senderName,
            partnerAccount = senderAccount
        )

        transferHistoryRepository.save(transferHistory)
        transferHistoryRepository.save(partnerTransferHistory)
    }

    fun decrease(account: Account, amount: Int) {
        accountManager.updateBalance(account, amount, DealType.DEPOSIT)
    }
}
