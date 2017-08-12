package com.smashpen.smashpen;

import com.smashpen.smashpen.domain.SmashCharacter;
import com.smashpen.smashpen.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@SpringBootApplication
public class CharacterListRestController {

	private CharacterRepository characterRepository;

	@Autowired
	CharacterListRestController(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/characters")
	List<SmashCharacter> characters() {
		return characterRepository.findAll();
	}

}