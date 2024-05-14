package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import user.entity.UserEntity

interface UserEntityRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}
