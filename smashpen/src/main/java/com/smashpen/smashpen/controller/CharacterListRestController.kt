package com.smashpen.smashpen.controller

import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.repository.CharacterRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CharacterListRestController(val characterRepository: CharacterRepository) {

    @CrossOrigin(origins = arrayOf("*"))
    @GetMapping("/characters")
    internal fun characters(): List<SmashCharacter> {
        return characterRepository.findAll()
    }

}
