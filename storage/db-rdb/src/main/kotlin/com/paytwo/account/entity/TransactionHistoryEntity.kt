package com.paytwo.account.entity

import com.paytwo.account.model.DealType
import com.paytwo.account.model.TransferHistory
import com.paytwo.support.BaseEntity
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class TransactionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val account: AccountEntity,
    val dealType: DealType,
    val amount: Int,
    val dealAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_account_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val partnerAccount: AccountEntity?,
    val partnerName: String?,

    val paymentReceiver: String?,
    val paymentMethod: String?
) : BaseEntity() {

    companion object {
        fun from(transferHistory: TransferHistory): TransactionHistoryEntity {
            return TransactionHistoryEntity(
                account = AccountEntity.from(transferHistory.account),
                dealType = transferHistory.dealType,
                amount = transferHistory.amount,
                dealAt = transferHistory.dealAt,
                partnerName = transferHistory.partnerName,
                partnerAccount = AccountEntity.from(transferHistory.partnerAccount),
                paymentReceiver = null,
                paymentMethod = null
            )
        }
    }

    fun toTransferHistory(): TransferHistory {
        return TransferHistory(
            id,
            account.toDomain(),
            dealType,
            amount,
            dealAt,
            partnerName!!,
            partnerAccount!!.toDomain()
        )
    }
}
