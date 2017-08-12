package com.smashpen.smashpen;

import com.smashpen.smashpen.account.Character;
import com.smashpen.smashpen.account.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
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

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/characters")
	List<Character> characters() {
		return characterRepository.findAll();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CharacterListRestController.class, args);
	}
}