package lt.verbus.verbusltapi.user.repository

import lt.verbus.verbusltapi.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}