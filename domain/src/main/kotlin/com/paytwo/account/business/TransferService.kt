package com.paytwo.account.business

import com.paytwo.account.implement.AccountReader
import com.paytwo.account.implement.TransferManager
import com.paytwo.support.error.DomainException
import com.paytwo.support.lock.AccountNumberLock
import com.paytwo.user.implement.UserReader
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TransferService(
    private val accountReader: AccountReader,
    private val transferManager: TransferManager,
    private val userReader: UserReader
) {

    private val logger = LoggerFactory.getLogger(TransferService::class.java)

    @Transactional
    @AccountNumberLock(2)
    fun sendMoney(
        senderAccountNumber: Long,
        receiverAccountNumber: Long,
        senderId: Long,
        senderName: String,
        amount: Int,
        dealAt: LocalDateTime,
        receiverBankName: String
    ) {
        logger.info("비즈니스 로직 시작!")

        // 각 계좌 조회 및 레코드 락 적용
        val senderAccount = accountReader.readByAccountNumber(senderAccountNumber)
        val receiverAccount = accountReader.readByAccountNumber(receiverAccountNumber)

        if (senderAccount.userId != senderId) {
            throw DomainException("계좌의 사용자 정보와 로그인한 사용자가 일치하지 않습니다", 400)
        }

        val receiver = userReader.readById(receiverAccount.userId)
        if (receiverBankName != receiverAccount.bankName) {
            throw DomainException("입금 계좌와 은행명이 일치하지 않습니다", 400)
        }

        transferManager.deposit(
            senderAccount,
            senderName,
            amount,
            dealAt,
            receiver.name,
            receiverAccount
        )
    }

    @Transactional
    @AccountNumberLock(1)
    fun decreaseMoney(
        accountNumber: Long,
        amount: Int
    ) {
        val account = accountReader.readByAccountNumber(accountNumber)
        transferManager.decrease(account, amount)
    }
}
