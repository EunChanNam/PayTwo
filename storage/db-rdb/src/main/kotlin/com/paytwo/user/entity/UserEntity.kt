package com.paytwo.user.entity

import com.paytwo.user.model.User
import com.paytwo.support.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class UserEntity(
    val name: String,
    @Column(unique = true)
    val username: String,
    val password: String,
    val notificationEnabled: Boolean
) : BaseEntity() {

    fun toDomain(): User {
        return User(
            id!!,
            name,
            username,
            password,
            notificationEnabled
        )
    }
}
