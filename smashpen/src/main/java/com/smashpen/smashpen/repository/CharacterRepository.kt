package com.smashpen.smashpen.repository

import com.smashpen.smashpen.domain.SmashCharacter
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterRepository : JpaRepository<SmashCharacter, Long> {
    fun findByName(name: String?): SmashCharacter
}