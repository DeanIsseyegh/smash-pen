package com.smashpen.smashpen.controller

import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.domain.User
import org.junit.Before
import org.junit.Test

import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.UserRepository
import com.smashpen.smashpen.service.CharacterNotesService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import java.util.*

class CharacterNotesRestControllerTest {

    private lateinit var controller: CharacterNotesRestController
    private lateinit var characterNotesRepository: CharacterNotesRepository
    private lateinit var userRepository: UserRepository
    private lateinit var characterNotesService: CharacterNotesService

    @Before
    fun setUp() {
        characterNotesRepository = mock(CharacterNotesRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        characterNotesService = mock(CharacterNotesService::class.java)
        controller = CharacterNotesRestController(userRepository, characterNotesRepository, characterNotesService)
    }

    @Test
    fun `Reads all character notes belonging to the user`() {
        val characters = listOf(CharacterNotes(mock(User::class.java), mock(SmashCharacter::class.java), ""))
        `when`(characterNotesRepository.findAllByUserId(3)).thenReturn(characters)
        assertThat(controller.readAllCharacterNotes(3), `is`(characters))
    }

    @Test
    fun `Reads a single characters notes belonging`() {
        val character = mock(CharacterNotes::class.java)
        `when`(characterNotesRepository.findById(3)).thenReturn(Optional.of(character))
        assertThat(controller.readSingleCharacterNotes(1, 3), `is`(character))
    }

    @Test
    fun `Given user exists, add or update character notes`() {
        val user = mockUserInRepo(3)
        val character = SmashCharacter("pika")
        val characterNotes = CharacterNotes(user, character, "This char sux!")
        val characterNotesDTO = CharacterNotesDTO("This char rox!", character)
        `when`(characterNotesService.createOrUpdateCharacterNotes(user, characterNotesDTO)).thenReturn(characterNotes)
        val response = controller.addOrUpdateCharacterNotes(3, characterNotesDTO)
        assertThat(characterNotes, `is`(characterNotes))
        assertThat(response.statusCode, `is`(ResponseEntity.ok(characterNotes).statusCode))
    }

    @Test
    fun `Given no user exists, return a 404`() {
        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val response = controller.addOrUpdateCharacterNotes(3, characterNotesDTO)
        assertThat(response, `is`(ResponseEntity.notFound().build()))
    }

    private fun mockUserInRepo(id: Long): User {
        val user = User(true, "dean", "pass")
        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        return user
    }

}
