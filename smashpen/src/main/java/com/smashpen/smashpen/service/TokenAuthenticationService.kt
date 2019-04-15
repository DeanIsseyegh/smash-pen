package com.smashpen.smashpen.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.smashpen.smashpen.security.AccountCredentials
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

    fun addAuthenticationToResponse(response: HttpServletResponse, username: String) {
        val expirationDate = clockService.calcCurrentDatePlusMilliseconds(tenDaysInMillis)
        val jwt = jwtService.buildToken(username, expirationDate, SECRET)
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt)
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        return if (token == null)
            null
        else
            makeAuthTokenIfUserExists(jwtService.parseUserFromToken(token, SECRET, TOKEN_PREFIX))
    }

    private fun makeAuthTokenIfUserExists(username: String?): Authentication? {
        return if (username == null) null else UsernamePasswordAuthenticationToken(username, null, emptyList<GrantedAuthority>())
    }

    //Cover via integration test
    fun readCredentialsFromRequest(req: HttpServletRequest): AccountCredentials {
        return ObjectMapper().readValue<AccountCredentials>(req.inputStream, AccountCredentials::class.java)
    }

}