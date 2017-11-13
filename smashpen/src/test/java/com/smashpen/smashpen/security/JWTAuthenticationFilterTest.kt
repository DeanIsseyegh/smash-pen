package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.TokenAuthenticationService
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JWTAuthenticationFilterTest {

    lateinit private var filter: JWTAuthenticationFilter

    lateinit private var request: HttpServletRequest
    lateinit private var response: ServletResponse
    lateinit private var tokenAuthenticationService: TokenAuthenticationService
    lateinit private var filterChain: FilterChain

    @Before
    fun setUp() {
        tokenAuthenticationService = mock(TokenAuthenticationService::class.java)
        filter = JWTAuthenticationFilter(tokenAuthenticationService)
        request = mock(HttpServletRequest::class.java)
        response = mock(ServletResponse::class.java)
        filterChain = mock(FilterChain::class.java)
    }

    @Test
    fun `Sets authentication on security context`() {
        val securityContext = mock(SecurityContext::class.java)
        val auth = mock(Authentication::class.java)
        SecurityContextHolder.setContext(securityContext)
        `when`(securityContext.authentication).thenReturn(auth)
        `when`(tokenAuthenticationService.getAuthentication(request)).thenReturn(auth)
        filter.doFilter(request, response, filterChain)
        verify(securityContext, times(1)).authentication = auth
    }

    @Test
    fun `Continues filter chain`() {
        filter.doFilter(request, response, filterChain)
        verify(filterChain, times(1)).doFilter(request, response)
    }

}