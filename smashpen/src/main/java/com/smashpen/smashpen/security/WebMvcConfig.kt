package com.smashpen.smashpen.security

import com.smashpen.smashpen.service.AccessValidationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(private val accessValidationService: AccessValidationService) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userValidationInterceptor()).addPathPatterns("/**/character")
        super.addInterceptors(registry)
    }

    @Bean
    fun userValidationInterceptor() : UserValidationInterceptor {
        return UserValidationInterceptor(accessValidationService)
    }

}