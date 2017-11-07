package com.smashpen.smashpen

import org.junit.Before
import org.junit.Test

import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.repository.CharacterNotesRepository
import com.smashpen.smashpen.repository.CharacterRepository
import com.smashpen.smashpen.repository.UserRepository
import org.hamcrest.Matchers.`is`

import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CharacterRestControllerTest {

    private lateinit var controller: CharacterRestController
    private lateinit var characterNotesRepository: CharacterNotesRepository
    private lateinit var characterRepository: CharacterRepository
    private lateinit var userRepository: UserRepository


    @Before
    fun setUp() {
        characterNotesRepository = mock(CharacterNotesRepository::class.java)
        characterRepository = mock(CharacterRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        controller = CharacterRestController(userRepository, characterNotesRepository, characterRepository)
    }

    @Test
    fun `readCharacters$production_sources_for_module_smashpen_main`() {

    }

    @Test
    fun `readCharacter$production_sources_for_module_smashpen_main`() {
    }

    @Test
    fun `add$production_sources_for_module_smashpen_main`() {
    }

}