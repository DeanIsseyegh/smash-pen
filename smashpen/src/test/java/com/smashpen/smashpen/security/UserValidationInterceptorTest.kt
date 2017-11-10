package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.AccessValidationService
import org.junit.Test

import org.junit.Before
import org.mockito.Mockito.*
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserValidationInterceptorTest {

    private lateinit var accessValidationService: AccessValidationService
    private lateinit var interceptor: UserValidationInterceptor

    @Before
    fun setUp() {
        accessValidationService = mock(AccessValidationService::class.java)
        interceptor = UserValidationInterceptor(accessValidationService)
    }

    @Test
    fun `Validates user id in request matches logged in user`() {
        val request = mock(HttpServletRequest::class.java)
        `when`(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                .thenReturn(mapOf(Pair("userId", "1")))
        interceptor.preHandle(request, mock(HttpServletResponse::class.java), Object())
        verify(accessValidationService, times(1)).validateUserIdMatchesLoggedInUser(1)
    }

    @Test
    fun `Does not attempt to validate user id if user id does not exist in request`() {
        val request = mock(HttpServletRequest::class.java)
        interceptor.preHandle(request, mock(HttpServletResponse::class.java), Object())
        verify(accessValidationService, never()).validateUserIdMatchesLoggedInUser(anyLong())
    }

    @Test(expected = NumberFormatException::class)
    fun `Does not attempt to validate user id if user id in request is malformed`() {
        val request = mock(HttpServletRequest::class.java)
        `when`(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                .thenReturn(mapOf(Pair("userId", "blah")))
        interceptor.preHandle(request, mock(HttpServletResponse::class.java), Object())
        verify(accessValidationService, never()).validateUserIdMatchesLoggedInUser(anyLong())
    }

}