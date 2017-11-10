package com.smashpen.smashpen.service

import com.smashpen.smashpen.controller.CharacterNotesDTO
import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CharacterNotesService internal constructor(private val characterNotesRepository: CharacterNotesRepository,
                                                 private val characterRepository: CharacterRepository) {

    fun createOrUpdateCharacterNotes(user: User, notesDTO: CharacterNotesDTO): CharacterNotes {
        val smashCharacter = characterRepository.findByName(notesDTO.smashCharacter.name)
        val existingNotes = characterNotesRepository.findBySmashCharacterAndUser(smashCharacter, user)
        return if (existingNotes == null)
            characterNotesRepository.save(CharacterNotes(user, smashCharacter, notesDTO.notes))
        else {
            existingNotes.notes = notesDTO.notes
            characterNotesRepository.save(existingNotes)
        }
    }

}