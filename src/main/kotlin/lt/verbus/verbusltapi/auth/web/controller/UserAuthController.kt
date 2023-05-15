package lt.verbus.verbusltapi.auth.web.controller

import lt.verbus.verbusltapi.auth.jwt.JwtTokenProvider
import lt.verbus.verbusltapi.auth.model.UserPrincipal
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

data class UserLoginRequest(
    val email: String,
    val password: String,
)

data class JwtAuthenticationResponse(
    val accessToken: String,
    val expiresAt: Instant,
)

@RestController
@RequestMapping("/auth")
class UserAuthController(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider,
    private val encoder: PasswordEncoder,
) {

    @PostMapping("/login")
    fun loginUser(@RequestBody @Validated request: UserLoginRequest): JwtAuthenticationResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email.lowercase(),
                request.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val userPrincipal = authentication.principal as UserPrincipal
        val jwt = tokenProvider.generateDefaultToken(userPrincipal)
        val jwtExpiresAt = tokenProvider.getTokenExpiresAtDate(jwt)
        return JwtAuthenticationResponse(
            accessToken = jwt,
            expiresAt = jwtExpiresAt,
        )
    }

    @PostMapping("/encode/{password}")
    fun encode(@PathVariable password: String): String = encoder.encode(password)
}