package com.smashpen.smashpen.repository

import com.smashpen.smashpen.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

fun UserRepository.findByUsernameOrNull(username: String): User? = this.findByUsername(username).orElse(null)

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}
