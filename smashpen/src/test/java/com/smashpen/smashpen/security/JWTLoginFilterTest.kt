package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.TokenAuthenticationService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilterTest {

    lateinit private var filter: JWTLoginFilter

    lateinit private var tokenAuthenticationService: TokenAuthenticationService
    lateinit private var authenticationManager: AuthenticationManager
    lateinit private var request: HttpServletRequest
    lateinit private var response: HttpServletResponse
    lateinit private var filterChain: FilterChain

    @Before
    fun setUp() {
        tokenAuthenticationService = mock(TokenAuthenticationService::class.java)
        authenticationManager = mock(AuthenticationManager::class.java)
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)
        filterChain = mock(FilterChain::class.java)
        filter = JWTLoginFilter("url", authenticationManager, tokenAuthenticationService)
    }

    @Test
    fun `Authenticates credentials from request`() {
        val accountCredentials = AccountCredentials("user", "pass")
        val expectedAuth = mock(Authentication::class.java)
        val argumentCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken::class.java)

        `when`(tokenAuthenticationService.readCredentialsFromRequest(request)).thenReturn(accountCredentials)
        `when`(authenticationManager.authenticate(argumentCaptor.capture())).thenReturn(expectedAuth)

        val result = filter.attemptAuthentication(request, response)

        assertThat(result, `is`(expectedAuth))
        assertThat(argumentCaptor.value.name, `is`("user"))
        assertThat(argumentCaptor.value.credentials as String, `is`("pass"))
        assertThat(argumentCaptor.value.authorities, `is`(emptyList()))
    }

    @Test
    fun `Adds authentication to response`() {
        val auth = mock(Authentication::class.java)
        `when`(auth.name).thenReturn("user")
        filter.successfulAuthentication(request, response, filterChain, auth)
        verify(tokenAuthenticationService, times(1)).addAuthenticationToResponse(response, "user")
    }

}