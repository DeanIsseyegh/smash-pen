package com.smashpen.smashpen

import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("*"))
@RestController
@RequestMapping("/{userId}/character")
class CharacterNotesRestController internal constructor(private val userRepository: UserRepository,
                                                        private val characterNotesRepository: CharacterNotesRepository,
                                                        private val characterRepository: CharacterRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @Throws(UserNotFoundException::class)
    internal fun readAllCharacterNotes(@PathVariable userId: Long): List<CharacterNotes> {
        return characterNotesRepository.findAllByUserId(userId)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/{characterNotesId}")
    @Throws(UserNotFoundException::class)
    internal fun readSingleCharacterNotes(@PathVariable userId: Long, @PathVariable characterNotesId: Long): CharacterNotes {
        return characterNotesRepository.findById(characterNotesId).get()
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT))
    @Throws(UserNotFoundException::class)
    internal fun addOrUpdateCharacterNotes(@PathVariable userId: Long, @RequestBody input: CharacterNotesDTO): ResponseEntity<CharacterNotes> {
        return userRepository.findById(userId).map { user ->
            val smashCharacter = characterRepository.findByName(input.smashCharacter.name)
            val existingNotes = characterNotesRepository.findBySmashCharacterAndUser(smashCharacter, user)
            val result = if (existingNotes == null)
                characterNotesRepository.save(CharacterNotes(user, smashCharacter, input.notes))
            else {
                existingNotes.notes = input.notes
                characterNotesRepository.save(existingNotes)
            }
            ResponseEntity.ok(result)
        }.orElse(ResponseEntity.notFound().build())
    }

}
