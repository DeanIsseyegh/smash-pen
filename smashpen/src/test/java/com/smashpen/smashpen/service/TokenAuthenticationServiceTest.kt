package com.smashpen.smashpen.service

import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.repository.UserRepository
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationServiceTest {

    lateinit private var service: TokenAuthenticationService
    lateinit private var clockService: ClockService
    lateinit private var jwtService: JwtService
    lateinit private var userRepository: UserRepository
    lateinit private var request: HttpServletRequest

    @Before
    fun setUp() {
        clockService = mock(ClockService::class.java)
        jwtService = mock(JwtService::class.java)
        request = mock(HttpServletRequest::class.java)
        userRepository = mock(UserRepository::class.java)
        service = TokenAuthenticationService(clockService, jwtService, userRepository)
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
    fun `Adds user id to response header along with JWT token`() {
        val resp = mock(HttpServletResponse::class.java)
        val date = Date()
        `when`(clockService.calcCurrentDatePlusMilliseconds(service.tenDaysInMillis)).thenReturn(date)
        `when`(jwtService.buildToken("user", date, service.SECRET)).thenReturn("token")
        val user = User(true, "user", "whatever")
        user.id = 99
        `when`(userRepository.findByUsername("user")).thenReturn(Optional.of(user))
        service.addAuthenticationToResponse(resp, "user")
        verify(resp, times(1)).addHeader("UserID", "99")
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
