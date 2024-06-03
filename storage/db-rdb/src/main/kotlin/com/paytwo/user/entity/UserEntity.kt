package com.paytwo.user.entity

import com.paytwo.support.BaseEntity
import com.paytwo.user.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    @Column(unique = true)
    val username: String,
    val password: String,
    val notificationEnabled: Boolean
) : BaseEntity() {

    fun toDomain(): User {
        return User(
            id,
            name,
            username,
            password,
            notificationEnabled
        )
    }
}
