package com.paytwo.user.repository

import com.paytwo.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?

    fun findUserEntityById(id: Long): UserEntity?
}
