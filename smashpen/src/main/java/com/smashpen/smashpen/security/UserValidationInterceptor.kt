package com.smashpen.smashpen.security

import com.smashpen.smashpen.UserNotFoundException
import com.smashpen.smashpen.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserValidationInterceptor : HandlerInterceptor {

    @Autowired lateinit var userRepository : UserRepository

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestAttrMap = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)
        if (requestAttrMap is Map<*,*>) {
            val userId = requestAttrMap.get("userId")
            if (userId is String)
                validateUserAccess(userId.toLong())
        }
        return super.preHandle(request, response, handler)
    }

    @Throws(UserNotFoundException::class)
    private fun validateUserAccess(userId: Long) {
        userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val currentUserName = SecurityContextHolder.getContext().authentication.name
        val currentUserId = userRepository.findByUsername(currentUserName).get().id
        if (userId != currentUserId) {
            throw SecurityException("Cannot access another users account")
        }
    }

}
