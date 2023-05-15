package lt.verbus.verbusltapi.auth.service

import lt.verbus.verbusltapi.auth.model.UserPrincipal
import lt.verbus.verbusltapi.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return UserPrincipal of (repository.findByEmail(username) ?: throw RuntimeException("User $username not found"))
    }
}