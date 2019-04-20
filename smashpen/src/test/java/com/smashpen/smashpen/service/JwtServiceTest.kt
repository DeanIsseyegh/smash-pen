package com.smashpen.smashpen.service

import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class JwtServiceTest {

    lateinit private var service: JwtService
    private val anEpochTime = 1510607283L

    @Before
    fun setUp() {
        service = JwtService()
    }

    @Test
    fun `builds token`() {
        val date = Date(anEpochTime)
        val resultToken = service.buildToken(1L, date, "ThisIsASecret")
        val expectedToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTEwNjA3fQ.6-XxlpAVE_FT9PE5C_PqK3sWPsu2iczmSeTHSclsUpiY2FKi9_LKKDrzNVpFVXdfPVdmT0LHcupmXOZ-VbKw7A"
        assertThat(resultToken, `is`(expectedToken))
    }

}
