package com.smashpen.smashpen.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class TokenAuthenticationService(val clockService: ClockService, val jwtService: JwtService) {

    val tenDaysInMillis: Long = 864000000
    val SECRET = "ThisIsASecret" //TODO Store this somewhere more secure before going live, e.g. AWS KMS or Vault
    val TOKEN_PREFIX = "Bearer"
    val HEADER_STRING = "Authorization"

    fun addAuthentication(response: HttpServletResponse, username: String) {
        val expirationDate = clockService.calcCurrentDatePlusMilliseconds(tenDaysInMillis)
        val jwt = jwtService.buildToken(username, expirationDate, SECRET)
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt)
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val user = jwtService.parseUserFromToken(token, SECRET, TOKEN_PREFIX)
            return if (user != null)
                UsernamePasswordAuthenticationToken(user, null, emptyList<GrantedAuthority>())
            else
                null
        }
        return null
    }

}