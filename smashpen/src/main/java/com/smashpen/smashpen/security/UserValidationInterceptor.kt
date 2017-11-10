package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.AccessValidationService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserValidationInterceptor internal constructor(val accessValidationService: AccessValidationService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestAttrMap = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)
        if (requestAttrMap is Map<*, *>) {
            val userId = requestAttrMap.get("userId")
            if (userId is String)
                accessValidationService.validateUserIdMatchesLoggedInUser(userId.toLong())
        }
        return super.preHandle(request, response, handler)
    }

}
