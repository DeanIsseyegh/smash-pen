package com.smashpen.smashpen.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.smashpen.smashpen.controller.UserNotFoundException
import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.security.AccountCredentials
import org.springframework.data.repository.findByIdOrNull
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

    fun addAuthenticationToResponse(response: HttpServletResponse, username: String) {
        val userId = userRepository.findByUsername(username).map { it.id }.orElseThrow { UserNotFoundException(username) }
        val expirationDate = clockService.calcCurrentDatePlusMilliseconds(tenDaysInMillis)
        val jwt = jwtService.buildToken(userId, expirationDate, SECRET)
        response.addHeader(AUTH_HEADER_KEY, "$TOKEN_PREFIX $jwt")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(AUTH_HEADER_KEY)
        return if (token == null) null else makeAuthTokenIfUserExists(token)
    }

    private fun makeAuthTokenIfUserExists(token: String): Authentication? {
        val userId = jwtService.parseUserIdFromToken(token, SECRET, TOKEN_PREFIX)
        val username = userRepository.findByIdOrNull(userId?.toLong())?.username
        return if (username == null)
            null
        else
            UsernamePasswordAuthenticationToken(username, null, emptyList<GrantedAuthority>())
    }

    //Cover via integration test
    fun readCredentialsFromRequest(req: HttpServletRequest): AccountCredentials {
        return ObjectMapper().readValue<AccountCredentials>(req.inputStream, AccountCredentials::class.java)
    }

}
