package com.paytwo.user.entity

import com.paytwo.user.model.User
import com.paytwo.user.support.BaseEntity
import jakarta.persistence.Entity

@Entity
class UserEntity(
    val name: String,
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
