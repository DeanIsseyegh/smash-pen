package com.smashpen.smashpen

import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.repository.CharacterRepository
import org.junit.Before
import org.junit.Test
import org.hamcrest.Matchers.`is`

import org.junit.Assert.*
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
    fun `Given_CharacterListIsReturnedFromRepo_Then_ReturnCharacterList`() {
        val expectedCharacterList = listOf(SmashCharacter("pikachu"))
        `when`(characterRepository.findAll()).thenReturn(expectedCharacterList)
        assertThat(controller.characters(), `is`(expectedCharacterList))
    }

}