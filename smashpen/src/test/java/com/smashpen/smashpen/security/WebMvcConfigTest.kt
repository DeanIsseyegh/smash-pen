package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.AccessValidationService
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*
import org.springframework.web.servlet.config.annotation.InterceptorRegistration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry

class WebMvcConfigTest {

    lateinit var config: WebMvcConfig
    lateinit var accessValidationService: AccessValidationService

    @Before
    fun setUp() {
        accessValidationService = mock(AccessValidationService::class.java)
        config = WebMvcConfig(accessValidationService)
    }

    @Test
    fun `Adds user validation interceptor to interceptor registry`() {
        val interceptorRegistry = mock(InterceptorRegistry::class.java)
        val interceptorRegistration = mock(InterceptorRegistration::class.java)
        `when`(interceptorRegistry.addInterceptor(any(UserValidationInterceptor::class.java))).thenReturn(interceptorRegistration)

        config.addInterceptors(interceptorRegistry)

        verify(interceptorRegistry, times(1)).addInterceptor(any(UserValidationInterceptor::class.java))
        verify(interceptorRegistration, times(1)).addPathPatterns("/**/character")
    }

    @Test
    fun `Creates user UserValidationInterceptor with correct dependencies`() {
        val userValidationInterceptor = config.userValidationInterceptor()
        assertThat(userValidationInterceptor.accessValidationService, `is`(accessValidationService))
    }

}