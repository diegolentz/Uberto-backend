package ar.edu.unsam.phm.uberto.security

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class TokenJwtUtil {

    //Firma del token
    @Value("\${jwt.secret.key}")
    lateinit var secretKey: String

    @Value("\${jwt.time.expiration}")
    var accessTokenMinutes: Int = 30

    @Value("\${jwt.user.generator}")
    var userGeneration: String = ""

    companion object ExpirationEnum{
        const val DEFAULT_EXPIRATION_TIME = 30
        const val ACCESS_TOKEN_EXPIRATION_TIME = 30
    }

    fun generate(userDetails: UserDetails, expirationDuration: Int = ExpirationEnum.DEFAULT_EXPIRATION_TIME, additionalClaims: Map<String, Any> = emptyMap()): String {
        return Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(Date().time + expirationDuration))
            .add(additionalClaims) //Todavia no se para que sirve, pero lo agregan - yo tampoco
            .and()
            .signWith(Keys.hmacShaKeyFor(this.secretKey.toByteArray()))
            .compact()
    }

    fun getAllClaims(jwtToken: String): Claims {
        val secretKey = Keys.hmacShaKeyFor(this.secretKey.toByteArray())
        val parser = Jwts.parser().verifyWith(secretKey).build()
        return parser.parseSignedClaims(jwtToken).payload
    }

    fun extractLoginIdentification(jwtToken: String): String?{
        return getAllClaims(jwtToken).subject
    }

    fun isExpired(jwtToken: String): Boolean{
        return getAllClaims(jwtToken)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }

    fun validateToken(token: String) : DecodedJWT {
        try{
            val algorithm = com.auth0.jwt.algorithms.Algorithm.HMAC256(secretKey)
            val verifier : JWTVerifier = JWT.require(algorithm).withIssuer(this.userGeneration).build()
            return verifier.verify(token)
        }catch (ex: JWTVerificationException){
            throw JWTVerificationException("Token invalido, no autorizado")
        }
    }

    fun extractUsername(decodedJWT: DecodedJWT): String{
        return decodedJWT.subject.toString()
    }

    fun getSpecificClaim(decodedJWT: DecodedJWT, claimName: String): Claim {
        return decodedJWT.getClaim(claimName)
    }


}