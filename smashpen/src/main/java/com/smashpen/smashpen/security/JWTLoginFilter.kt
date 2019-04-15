package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.TokenAuthenticationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

class JWTLoginFilter(url: String, authManager: AuthenticationManager, private val tokenAuthenticationService: TokenAuthenticationService)
    : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val (username, password) = tokenAuthenticationService.readCredentialsFromRequest(req)
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password, emptyList<GrantedAuthority>())
        )
    }

    @Throws(IOException::class, ServletException::class)
    override public fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain?, auth: Authentication) {
        tokenAuthenticationService.addAuthenticationToResponse(res, auth.name)
    }
}
