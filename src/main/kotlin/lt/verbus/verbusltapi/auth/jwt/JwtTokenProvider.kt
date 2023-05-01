package lt.verbus.verbusltapi.auth.jwt

import lt.verbus.verbusltapi.auth.model.UserPrincipal
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.InvalidClaimException
import com.auth0.jwt.interfaces.DecodedJWT
import lt.verbus.verbusltapi.exception.UnauthorizedException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtTokenProvider {
    private val jwtSecret = "some-secret"
    private val jwtExpirationInMinutes = 5

    private val CLAIMS_EMAIL = "e"
    private val CLAIMS_NAME = "n"
    private val CLAIMS_SURNAME = "s"
    private val CLAIMS_AUTHORITIES = "a"

    fun generateDefaultToken(userPrincipal: UserPrincipal): String? {
        return generateToken(userPrincipal, jwtExpirationInMinutes)
    }

    fun generateToken(principal: UserPrincipal, expirationInMs: Int): String? {
        val now = Date()
        val expiryDate = Date(now.time + expirationInMs)
        return JWT.create()
            .withSubject(java.lang.String.valueOf(principal.getUserId()))
            .withIssuedAt(now)
            .withExpiresAt(expiryDate)
            .withClaim(CLAIMS_AUTHORITIES, principal.authorities.map { obj: GrantedAuthority -> obj.authority })
            .withClaim(CLAIMS_EMAIL, principal.getEmail())
            .withClaim(CLAIMS_SURNAME, principal.getLastName())
            .withClaim(CLAIMS_NAME, principal.getName())
            .sign(Algorithm.HMAC512(jwtSecret))
    }

    fun getUserPrincipalFromToken(token: String?): UserPrincipal? {
        val claims: DecodedJWT = JWT.require(Algorithm.HMAC512(jwtSecret))
            .build()
            .verify(token)
        val iat: Date? = claims.getClaim("iat").asDate()
        val exp: Date? = claims.getClaim("exp").asDate()
        if (iat == null || exp == null || exp.time - iat.time > jwtExpirationInMinutes) throw InvalidClaimException(
            "Bad dates $iat $exp $jwtExpirationInMinutes"
        )

        return UserPrincipal.of(
            claims.subject.toLong(),
            claims.getClaim(CLAIMS_EMAIL).asString(),
            claims.getClaim(CLAIMS_NAME).asString(),
            claims.getClaim(CLAIMS_SURNAME).asString(),

            claims.getClaim(CLAIMS_AUTHORITIES)
                .asList(String::class.java)
                .map(::SimpleGrantedAuthority)
                .toSet()
        )
    }

    fun getTokenExpiresAtDate(jwtToken: String?): Instant? {
        val claims: DecodedJWT = JWT.require(Algorithm.HMAC512(jwtSecret))
            .build()
            .verify(jwtToken)
        val exp: Date? = claims.getClaim("exp").asDate()
        if (exp != null) {
            return exp.toInstant()
        }
        throw UnauthorizedException()
    }
}