package com.paytwo.user.repository

import com.paytwo.user.entity.UserEntity
import com.paytwo.user.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userEntityRepository: UserEntityRepository
) : UserRepository {

    override fun save(user: User): User {
        val userEntity = UserEntity(
            name = user.name,
            username = user.username,
            password = user.password,
            notificationEnabled = user.notificationEnabled
        )
        userEntityRepository.save(userEntity)

        return userEntity.toDomain()
    }

    override fun findByUsername(username: String): User? {
        return userEntityRepository.findByUsername(username)?.toDomain()
    }

    override fun findById(id: Long): User? {
        return userEntityRepository.findUserEntityById(id)?.toDomain()
    }
}
