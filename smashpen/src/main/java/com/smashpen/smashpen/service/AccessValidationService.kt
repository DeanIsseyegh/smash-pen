package com.smashpen.smashpen.service

import com.smashpen.smashpen.controller.UserNotFoundException
import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.repository.findByUsernameOrNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AccessValidationService internal constructor(private val userRepository: UserRepository) {

    @Throws(UserNotFoundException::class)
    fun validateUserIdMatchesLoggedInUser(userId: Long) {
        userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException(userId)
        val currentUserName = SecurityContextHolder.getContext().authentication.name
        val currentUserId = userRepository.findByUsernameOrNull(currentUserName)?.id
                ?: throw UserNotFoundException(currentUserName)
        if (userId != currentUserId) {
            throw SecurityException("Cannot access another users account")
        }
    }
}
