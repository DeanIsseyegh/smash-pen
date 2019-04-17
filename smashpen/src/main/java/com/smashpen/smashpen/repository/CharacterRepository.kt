package com.smashpen.smashpen.repository

import com.smashpen.smashpen.domain.SmashCharacter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository : JpaRepository<SmashCharacter, Long> {
    fun findByName(name: String?): SmashCharacter
}
