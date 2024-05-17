package com.paytwo.account.entity

import com.paytwo.account.model.Account
import com.paytwo.support.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class AccountEntity(
    val bankName: String,
    @Column(unique = true)
    val accountNumber: Long,
    val balance: Int,
    val userId: Long
) : BaseEntity() {

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
