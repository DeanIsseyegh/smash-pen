package com.smashpen.smashpen.security

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.web.servlet.config.annotation.InterceptorRegistration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry

class WebMvcConfigTest {

    lateinit var config: WebMvcConfig
    lateinit var userValidationInterceptor: UserValidationInterceptor

    @Before
    fun setUp() {
        userValidationInterceptor = mock(UserValidationInterceptor::class.java)
        config = WebMvcConfig(userValidationInterceptor)
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

}
