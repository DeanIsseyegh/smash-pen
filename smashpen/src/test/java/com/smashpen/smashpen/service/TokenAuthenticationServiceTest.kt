package com.smashpen.smashpen.service

import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationServiceTest {

    lateinit private var service: TokenAuthenticationService
    lateinit private var clockService: ClockService
    lateinit private var jwtService: JwtService
    lateinit private var request: HttpServletRequest

    @Before
    fun setUp() {
        clockService = mock(ClockService::class.java)
        jwtService = mock(JwtService::class.java)
        request = mock(HttpServletRequest::class.java)
        service = TokenAuthenticationService(clockService, jwtService)
    }

    @Test
    fun `Builds a JWT key and adds it to response header`() {
        val resp = mock(HttpServletResponse::class.java)
        val date = Date()
        `when`(clockService.calcCurrentDatePlusMilliseconds(service.tenDaysInMillis)).thenReturn(date)
        `when`(jwtService.buildToken("user", date, service.SECRET)).thenReturn("token")
        service.addAuthenticationToResponse(resp, "user")
        verify(resp, times(1)).addHeader("Authorization", "Bearer token")
    }

    @Test
    fun `Given no authorization header then return null`() {
        assertNull(service.getAuthentication(request))
    }

    @Test
    fun `Given authorization header with with no user in token then return null`() {
        `when`(request.getHeader("Authorization")).thenReturn("token")
        `when`(jwtService.parseUserFromToken("token", service.SECRET, service.TOKEN_PREFIX)).thenReturn(null)
        assertNull(service.getAuthentication(request))
    }

    @Test //Should be integration test
    fun `Reads credentials from request`() {
        //
    }

}