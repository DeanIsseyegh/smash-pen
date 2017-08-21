package com.smashpen.smashpen

import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.net.URI

@CrossOrigin(origins = arrayOf("*"))
@RestController
@RequestMapping("/{userId}/character")
class CharacterRestController @Autowired
internal constructor(private val userRepository: UserRepository,
                     private val characterNotesRepository: CharacterNotesRepository,
                     private val characterRepository: CharacterRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @Throws(UserNotFoundException::class)
    internal fun readCharacters(@PathVariable userId: Long?): Collection<CharacterNotes> {
        this.validateUser(userId)
        return this.characterNotesRepository.findAllByUserId(userId)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/{characterNotesId}")
    @Throws(UserNotFoundException::class)
    internal fun readCharacter(@PathVariable userId: Long?, @PathVariable characterNotesId: Long?): CharacterNotes {
        this.validateUser(userId)
        return this.characterNotesRepository.findOne(characterNotesId)
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @Throws(UserNotFoundException::class)
    internal fun add(@PathVariable userId: Long?, @RequestBody input: CharacterNotes): ResponseEntity<*> {
        this.validateUser(userId)
        return this.userRepository.findById(userId).map { account ->
            val smashCharacter = characterRepository.findByName(input.smashCharacter?.name)
            val existingNotes = characterNotesRepository.findCharacterNotesBySmashCharacterAndUser(smashCharacter, account)
            val result = if (existingNotes == null)
                characterNotesRepository.save(CharacterNotes(account, smashCharacter, input.notes))
            else
                characterNotesRepository.save(existingNotes)
            val location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(result.id!!).toUri()
            ResponseEntity.created(location).build<Any>()
        }
                .orElse(ResponseEntity.noContent().build())
    }


    @Throws(UserNotFoundException::class)
    private fun validateUser(userId: Long?) {
        this.userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val currentUserId = this.userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name).get().id
        if (userId != currentUserId) {
            throw SecurityException("Cannot access another users account")
        }
    }
}
