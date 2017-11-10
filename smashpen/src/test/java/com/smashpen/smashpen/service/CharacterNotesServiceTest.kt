package com.smashpen.smashpen.service

import com.smashpen.smashpen.controller.CharacterNotesDTO
import com.smashpen.smashpen.domain.CharacterNotes
import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.domain.User
import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.Before

import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

class CharacterNotesServiceTest {

    private lateinit var service: CharacterNotesService
    private lateinit var characterNotesRepository: CharacterNotesRepository
    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setUp() {
        characterRepository = mock(CharacterRepository::class.java)
        characterNotesRepository = mock(CharacterNotesRepository::class.java)
        service = CharacterNotesService(characterNotesRepository, characterRepository)
    }

    @Test
    fun `Given a user already has a smash character notes attached, update existing one with new notes`() {
        val user = User(true, "dean", "pass")
        val character = mockCharacterInRepo("pika")
        val characterNotes = mockCharacterNotesInRepo(user, character, "This char sux!")

        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val expectedSavedNotes = mockCharacterNotesRepoSave(user)
        val savedNotes = service.createOrUpdateCharacterNotes(user, characterNotesDTO)

        verify(characterNotesRepository, Mockito.times(1)).save(characterNotes)
        assertThat(characterNotes.notes, Matchers.`is`("This char rox!"))
        assertThat(savedNotes, `is`(expectedSavedNotes))
    }

    @Test
    fun `Saves new character notes if none exist`() {
        val user = User(true, "dean", "pass")
        val character = mockCharacterInRepo("pika")

        val characterNotesDTO = CharacterNotesDTO("This char rox!", SmashCharacter("pika"))
        val expectedSavedNotes = mockCharacterNotesRepoSave(user)
        val savedNotes = service.createOrUpdateCharacterNotes(user, characterNotesDTO)

        val argument = ArgumentCaptor.forClass(CharacterNotes::class.java)
        verify(characterNotesRepository).save(argument.capture())
        assertThat(argument.value.notes, `is`("This char rox!"))
        assertThat(argument.value.user, Matchers.`is`(user))
        assertThat(argument.value.smashCharacter, Matchers.`is`(character))
        assertThat(savedNotes, `is`(expectedSavedNotes))
    }

    private fun mockCharacterNotesRepoSave(user: User): CharacterNotes {
        val expectedNotes = CharacterNotes(user, SmashCharacter("pika"), "This char rox!")
        `when`(characterNotesRepository.save(any(CharacterNotes::class.java))).thenReturn(expectedNotes)
        return expectedNotes
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

}