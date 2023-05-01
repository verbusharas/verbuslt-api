package lt.verbus.verbusltapi.auth.service

import lt.verbus.verbusltapi.auth.model.UserPrincipal
import lt.verbus.verbusltapi.exception.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CurrentUserService(
) {
    fun getUserId() = getPrincipal().getUserId();

    private fun getPrincipal(): UserPrincipal {
        val auth = SecurityContextHolder.getContext().authentication ?: throw UnauthorizedException()
        val principal = auth.principal
        if (principal is UserPrincipal) {
            return principal
        }

        throw UnauthorizedException()
    }
}