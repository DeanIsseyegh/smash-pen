package com.smashpen.smashpen.repository

import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.domain.CharacterNotes
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterNotesRepository : JpaRepository<CharacterNotes, Long> {
    fun findAllByUserId(id: Long?): List<CharacterNotes>
    fun findBySmashCharacterAndUser(smashCharacter: SmashCharacter, user: User): CharacterNotes?
}