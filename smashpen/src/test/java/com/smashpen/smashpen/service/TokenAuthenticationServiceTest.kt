package com.smashpen.smashpen.service

import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.repository.UserRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationServiceTest {

    private lateinit var service: TokenAuthenticationService
    private lateinit var clockService: ClockService
    private lateinit var jwtService: JwtService
    private lateinit var userRepository: UserRepository
    private lateinit var request: HttpServletRequest

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

        val userId = 4L
        val user = createUser(userId)

        `when`(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user))
        `when`(clockService.calcCurrentDatePlusMilliseconds(service.tenDaysInMillis)).thenReturn(date)
        `when`(jwtService.buildToken(userId, date, service.SECRET)).thenReturn("token")
        service.addAuthenticationToResponse(resp, "testUsername")
        verify(resp, times(1)).addHeader("Authorization", "Bearer token")
    }

    @Test
    fun `Given no authorization header then return null`() {
        assertNull(service.getAuthentication(request))
    }

    @Test
    fun `Given authorization header with with no user in token then return null`() {
        `when`(request.getHeader("Authorization")).thenReturn("token")
        `when`(jwtService.parseUserIdFromToken("token", service.SECRET, service.TOKEN_PREFIX)).thenReturn(null)
        assertNull(service.getAuthentication(request))
    }

    @Test
    fun `Given authorization header with with user in token then return user object`() {
        val userId = 4L
        val user = createUser(userId)

        `when`(request.getHeader("Authorization")).thenReturn("token")
        `when`(jwtService.parseUserIdFromToken("token", service.SECRET, service.TOKEN_PREFIX)).thenReturn(userId.toString())
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        val authentication = service.getAuthentication(request)

        assertNotNull(service.getAuthentication(request))
        assertThat(authentication?.name, `is`("testUsername"))
    }

    private fun createUser(userId: Long): User {
        val user = User(true, "testUsername", "whatever")
        user.id = userId
        return user
    }

    @Test //Should be integration test
    fun `Reads credentials from request`() {
        //
    }

}
