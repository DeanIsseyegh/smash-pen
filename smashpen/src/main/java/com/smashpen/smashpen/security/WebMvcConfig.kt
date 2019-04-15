package com.smashpen.smashpen.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(private val userValidationInterceptor: UserValidationInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userValidationInterceptor).addPathPatterns("/**/character")
        super.addInterceptors(registry)
    }

}
