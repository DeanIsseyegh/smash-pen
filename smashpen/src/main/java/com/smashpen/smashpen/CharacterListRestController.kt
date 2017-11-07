package com.smashpen.smashpen

import com.smashpen.smashpen.domain.SmashCharacter
import com.smashpen.smashpen.repository.CharacterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin

@RestController
class CharacterListRestController(val characterRepository: CharacterRepository) {

    @CrossOrigin(origins = arrayOf("*"))
    @GetMapping("/characters")
    internal fun characters(): List<SmashCharacter> {
        return characterRepository.findAll()
    }

}