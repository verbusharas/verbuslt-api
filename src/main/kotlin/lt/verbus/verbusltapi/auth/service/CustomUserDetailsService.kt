package lt.verbus.verbusltapi.auth.service

import lt.verbus.verbusltapi.auth.model.UserPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = UserPrincipal.of(
        userId = 1L,
        email = "some@email.com",
        name = "Some Name",
        surname = "Some Surname",
        roles = setOf(SimpleGrantedAuthority("SOME_ROLE"))
    )
}