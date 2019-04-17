package com.smashpen.smashpen.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.security.AccountCredentials
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class TokenAuthenticationService(val clockService: ClockService, val jwtService: JwtService, val userRepository: UserRepository) {

    val tenDaysInMillis: Long = 864000000
    val SECRET = "ThisIsASecret" //TODO Store this somewhere more secure before going live, e.g. AWS KMS or Vault
    val TOKEN_PREFIX = "Bearer"
    val AUTH_HEADER_KEY = "Authorization"
    val USER_ID_HEADER_KEY = "UserID"

    fun addAuthenticationToResponse(response: HttpServletResponse, username: String) {
        val expirationDate = clockService.calcCurrentDatePlusMilliseconds(tenDaysInMillis)
        val jwt = jwtService.buildToken(username, expirationDate, SECRET)
        response.addHeader(AUTH_HEADER_KEY, TOKEN_PREFIX + " " + jwt)
        val userId = userRepository.findByUsername(username).map { it.id }
        response.addHeader(USER_ID_HEADER_KEY, userId.orElse(-1L).toString())
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(AUTH_HEADER_KEY)
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
