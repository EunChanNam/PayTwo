package user.entity

import com.paytwo.user.model.User
import jakarta.persistence.Entity
import user.support.BaseEntity

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
