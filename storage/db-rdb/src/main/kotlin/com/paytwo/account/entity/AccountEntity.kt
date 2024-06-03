package com.paytwo.account.entity

import com.paytwo.account.model.Account
import com.paytwo.support.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val bankName: String,
    @Column(unique = true)
    val accountNumber: Long,
    val balance: Int,
    val userId: Long
) : BaseEntity() {

    companion object {
        fun from(account: Account): AccountEntity {
            return AccountEntity(
                id = account.id,
                bankName = account.bankName,
                accountNumber = account.accountNumber,
                balance = account.balance,
                userId = account.userId
            )
        }
    }

    fun toDomain(): Account {
        return Account(
            id,
            bankName,
            accountNumber,
            balance,
            userId
        )
    }
}
