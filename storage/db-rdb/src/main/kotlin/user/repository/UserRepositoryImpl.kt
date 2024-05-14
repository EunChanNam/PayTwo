package user.repository

import com.paytwo.user.model.User
import com.paytwo.user.repository.UserRepository
import user.entity.UserEntity

class UserRepositoryImpl(
    private val userEntityRepository: UserEntityRepository
) : UserRepository {

    override fun save(user: User): User {
        val userEntity = UserEntity(
            user.name,
            user.username,
            user.password,
            user.notificationEnabled
        )
        userEntityRepository.save(userEntity)

        return userEntity.toDomain()
    }

    override fun findByUsername(username: String): User? {
        return userEntityRepository.findByUsername(username)?.toDomain()
    }
}
