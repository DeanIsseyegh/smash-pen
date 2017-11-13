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
        val resultToken = service.buildToken("user", date, "ThisIsASecret")
        val expectedToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTEwNjA3fQ.EBeuuglrBv73hj-kotGO48eknA7vFA25dcuFBBpRLj1Ric7EQmDACXyFTUREzDPUXGleBZg7t1w6-BLJnOTfGQ"
        assertThat(resultToken, `is`(expectedToken))
    }

}