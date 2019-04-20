package com.smashpen.smashpen.service

import com.smashpen.smashpen.controller.UserNotFoundException
import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.repository.UserRepository
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class AccessValidationServiceTest {

    private lateinit var service: AccessValidationService
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        service = AccessValidationService(userRepository)
    }

    @Test(expected = UserNotFoundException::class)
    fun `If no user exists, throw user not found exception`() {
        service.validateUserIdMatchesLoggedInUser(1)
    }

    @Test(expected = SecurityException::class)
    fun `If user id passed in does match logged in user, throw security exception`() {
        mockedLoggedInUser("bob")
        val user = User(true, "bob", "pass")
        user.id = 3
        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))
        `when`(userRepository.findByUsername("bob")).thenReturn(Optional.of(user))
        service.validateUserIdMatchesLoggedInUser(1)
    }

    @Test
    fun `If user id passed in matches logged in user, do not throw any exceptions`() {
        mockedLoggedInUser("bob")
        val user = User(true, "bob", "pass")
        user.id = 3
        `when`(userRepository.findById(3)).thenReturn(Optional.of(user))
        `when`(userRepository.findByUsername("bob")).thenReturn(Optional.of(user))
        service.validateUserIdMatchesLoggedInUser(3)
    }

    @Test(expected = UserNotFoundException::class)
    fun `If current user's username does not exist, then throw exception`() {
        mockedLoggedInUser("bob")
        val user = User(true, "bob", "pass")
        user.id = 3
        `when`(userRepository.findById(3)).thenReturn(Optional.of(user))
        `when`(userRepository.findByUsername("bob")).thenReturn(Optional.empty())
        service.validateUserIdMatchesLoggedInUser(3)
    }

    private fun mockedLoggedInUser(username: String) {
        val securityContext = mock(SecurityContext::class.java)
        val auth = mock(Authentication::class.java)
        SecurityContextHolder.setContext(securityContext)
        `when`(securityContext.authentication).thenReturn(auth)
        `when`(auth.name).thenReturn(username)
    }

}
