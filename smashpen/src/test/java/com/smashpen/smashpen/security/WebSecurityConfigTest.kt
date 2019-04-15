package com.smashpen.smashpen.security

import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer

class WebSecurityConfigTest {

    lateinit var config: WebSecurityConfig

    @Before
    fun setUp() {
        config = WebSecurityConfig()
    }

    @Test
    fun `Configures http security`() {
//        val httpSecurity = mock(HttpSecurity::class.java)
//        val csrcConfigurer: CsrfConfigurer<HttpSecurity> = mockk()
//        `when`(httpSecurity.csrf()).thenReturn(csrcConfigurer)
//
//        every { csrcConfigurer.disable() } returns mockk()
//
//        config.configure(httpSecurity)
    }

}
