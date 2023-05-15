package lt.verbus.verbusltapi.auth.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lt.verbus.verbusltapi.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    private var userId: Long,

    @JsonIgnore
    private var password: String? = null,
    private var email: String,
    private var firstName: String,
    private var lastName: String,
    private var enabled: Boolean,
    private var authorities: Collection<GrantedAuthority>,
): UserDetails {

    fun getUserId() = userId
    fun getEmail() = email
    fun getFirstName() = firstName
    fun getLastName() = lastName

    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled

    companion object {
        fun of(
            userId: Long,
            email: String,
            password: String,
            name: String,
            surname: String,
            roles: Set<GrantedAuthority>
        ) = UserPrincipal(
            userId = userId,
            authorities = roles,
            email = email,
            password = password,
            enabled = true,
            firstName = name,
            lastName = surname,
        )

        fun of(
            userId: Long,
            email: String,
            name: String,
            surname: String,
            roles: Set<GrantedAuthority>
        ) = UserPrincipal(
            userId = userId,
            authorities = roles,
            email = email,
            enabled = true,
            firstName = name,
            lastName = surname,
        )

        infix fun of(
            user: User,
        ) = UserPrincipal(
            userId = user.id,
            authorities = setOf(SimpleGrantedAuthority(user.role.toString())),
            email = user.email,
            password = user.password,
            enabled = true,
            firstName = user.firstName,
            lastName = user.lastName,
        )
    }
}