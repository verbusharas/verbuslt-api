package lt.verbus.verbusltapi.auth.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(val tokenProvider: JwtTokenProvider): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractJwtFromRequest(request)
            if (token != null) {
                val principal = tokenProvider.getUserPrincipalFromToken(token)
                val authentication = UserPrincipalAuthentication(principal)
                SecurityContextHolder.getContext().authentication = authentication
                print("JWT Token Provided - setting authentication in security context")
            } else {
                print("No JWT Token Provided")
            }
        } catch (e: Exception) {
            print("JWT Authentication error $e")
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        // Try getting JWT from auth header
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else request.getParameter("auth_token")

        // Try getting JWT from get parameter
    }
}