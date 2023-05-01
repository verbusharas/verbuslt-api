package lt.verbus.verbusltapi.auth.jwt

import lt.verbus.verbusltapi.auth.model.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken

class UserPrincipalAuthentication: AbstractAuthenticationToken {
    private var userPrincipal: UserPrincipal? = null
    constructor(): super(null) {
        super.setAuthenticated(false)
    }
    constructor(principal: UserPrincipal): super(principal.authorities) {
        super.setAuthenticated(true)
        this.userPrincipal = principal
    }

    override fun getCredentials(): String? = null

    override fun getPrincipal(): UserPrincipal? = userPrincipal
}