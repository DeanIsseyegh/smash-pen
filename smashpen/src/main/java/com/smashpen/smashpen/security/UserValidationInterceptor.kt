package com.smashpen.smashpen.security

import com.smashpen.smashpen.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/*

class UserValidationInterceptor : HandlerInterceptorAdapter() {

    @Autowired lateinit var userRepository : UserRepository

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("Im here....")
        return super.preHandle(request, response, handler)
    }


}*/
