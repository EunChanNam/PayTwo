package com.paytwo

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String?,
    @Value("\${jwt.token-validity-in-seconds}")
    private val tokenValidSeconds: Int,
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
) {

    fun getClaim(token: String): Any {
        val claimsBody: Claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claimsBody["payload"] ?: throw IllegalArgumentException("Token Payload is null!!")
    }

    fun createAccessToken(payload: Any): String {
        val now = Date()

        return Jwts.builder()
            .setHeaderParam("type", "jwt")
            .claim("payload", payload)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidSeconds * 1000L))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
