package com.smashpen.smashpen.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    fun buildToken(userId: Long, expirationDate: Date, secret: String): String {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun parseUserIdFromToken(token: String, secret: String, tokenPrefix: String): String? {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(tokenPrefix, ""))
                .body
                .subject
    }

}
