package com.smashpen.smashpen.service

import com.smashpen.smashpen.controller.UserNotFoundException
import com.smashpen.smashpen.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AccessValidationService internal constructor(private val userRepository: UserRepository) {

    @Throws(UserNotFoundException::class)
    fun validateUserIdMatchesLoggedInUser(userId: Long) {
        userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val currentUserName = SecurityContextHolder.getContext().authentication.name
        val currentUserId = userRepository.findByUsername(currentUserName).get().id
        if (userId != currentUserId) {
            throw SecurityException("Cannot access another users account")
        }
    }
}