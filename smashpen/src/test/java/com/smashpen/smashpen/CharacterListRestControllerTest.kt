package com.smashpen.smashpen

import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.repository.CharacterRepository
import org.junit.Before
import org.junit.Test
import org.hamcrest.Matchers.`is`

import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CharacterListRestControllerTest {

    private lateinit var controller: CharacterListRestController
    private lateinit var characterRepository : CharacterRepository

    @Before
    fun setUp() {
        characterRepository = mock(CharacterRepository::class.java)
        controller = CharacterListRestController(characterRepository)
    }

    @Test
    fun `Given a character list is returned from the repo then return the character list`() {
        val expectedCharacterList = listOf(SmashCharacter("pikachu"))
        `when`(characterRepository.findAll()).thenReturn(expectedCharacterList)
        assertThat(controller.characters(), `is`(expectedCharacterList))
    }

}