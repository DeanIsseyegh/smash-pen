package com.smashpen.smashpen

import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.domain.User
import org.junit.Before
import org.junit.Test

import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import com.smashpen.smashpen.repository.UserRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import java.util.*

class CharacterNotesRestControllerTest {

    private lateinit var controller: CharacterNotesRestController
    private lateinit var characterNotesRepository: CharacterNotesRepository
    private lateinit var characterRepository: CharacterRepository
    private lateinit var userRepository: UserRepository


    @Before
    fun setUp() {
        characterNotesRepository = mock(CharacterNotesRepository::class.java)
        characterRepository = mock(CharacterRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        controller = CharacterNotesRestController(userRepository, characterNotesRepository, characterRepository)
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
    fun `Given a user already has a smash character notes attached, update existing one with new notes`() {
        val user = mockUserInRepo(3)
        val character = mockCharacterInRepo("pika")
        val characterNotes = mockCharacterNotesInRepo(user, character, "This char sux!")

        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val response = controller.addOrUpdateCharacterNotes(3, characterNotesDTO)

        verify(characterNotesRepository, times(1)).save(characterNotes)
        assertThat(characterNotes.notes, `is`("This char rox!"))
        assertThat(response.statusCode, `is`(ResponseEntity.ok(characterNotes).statusCode))
    }

    @Test
    fun `Given a user already has no smash character notes attached, create new one`() {
        val user = mockUserInRepo(3)
        val character = mockCharacterInRepo("pika")

        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val response = controller.addOrUpdateCharacterNotes(3, characterNotesDTO)

        val argument = ArgumentCaptor.forClass(CharacterNotes::class.java)
        verify(characterNotesRepository).save(argument.capture())
        assertThat(argument.value.notes, `is`("This char rox!"))
        assertThat(argument.value.user, `is`(user))
        assertThat(argument.value.smashCharacter, `is`(character))

        assertThat(response.statusCode, `is`(ResponseEntity.ok(argument.value).statusCode))
    }

    @Test
    fun `Given no user exists, return a 404`() {
        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val response = controller.addOrUpdateCharacterNotes(3, characterNotesDTO)
        assertThat(response, `is`(ResponseEntity.notFound().build()))
    }

    private fun mockCharacterNotesInRepo(user: User, character: SmashCharacter, notes: String): CharacterNotes {
        val characterNotes = CharacterNotes(user, character, notes)
        `when`(characterNotesRepository.findBySmashCharacterAndUser(character, user)).thenReturn(characterNotes)
        return characterNotes
    }

    private fun mockCharacterInRepo(name: String): SmashCharacter {
        val character = SmashCharacter(name)
        `when`(characterRepository.findByName(name)).thenReturn(character)
        return character
    }

    private fun mockUserInRepo(id: Long): User {
        val user = User(true, "dean", "pass")
        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        return user
    }

}