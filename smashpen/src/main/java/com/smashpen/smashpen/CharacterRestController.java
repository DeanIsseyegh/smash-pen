package com.smashpen.smashpen;

import com.smashpen.smashpen.account.AccountRepository;
import com.smashpen.smashpen.account.Character;
import com.smashpen.smashpen.account.CharacterNotes;
import com.smashpen.smashpen.account.CharacterNotesRepository;
import com.smashpen.smashpen.account.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/{userId}/character")
public class CharacterRestController {

	private AccountRepository accountRepository;
	private CharacterNotesRepository characterNotesRepository;
	private CharacterRepository characterRepository;

	@Autowired
	CharacterRestController(AccountRepository accountRepository,
							CharacterNotesRepository characterNotesRepository,
							CharacterRepository characterRepository) {
		this.accountRepository = accountRepository;
		this.characterNotesRepository = characterNotesRepository;
		this.characterRepository = characterRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<CharacterNotes> readCharacters(@PathVariable Long userId) throws UserNotFoundException {
		this.validateUser(userId);
		return this.characterNotesRepository.findAllByAccountId(userId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	CharacterNotes readCharacter(@PathVariable Long userId, @PathVariable Long characterNotesId) throws UserNotFoundException {
		this.validateUser(userId);
		return this.characterNotesRepository.findOne(characterNotesId);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable Long userId, @RequestBody CharacterNotes input) throws UserNotFoundException {
		this.validateUser(userId);
		return this.accountRepository.findById(userId).map(account -> {
				Character character = characterRepository.findByName(input.getCharacter().getName());
				CharacterNotes existingNotes = characterNotesRepository.findCharacterNotesByCharacterAndAccount(character, account);
				CharacterNotes result;
				if (existingNotes != null) {
					existingNotes.setNotes(input.getNotes());
					result = characterNotesRepository.save(existingNotes);
				} else {
					result = characterNotesRepository.save(new CharacterNotes(account, character, input.getNotes()));
				}
				URI location = ServletUriComponentsBuilder
							.fromCurrentRequest().path("/{id}")
							.buildAndExpand(result.getId()).toUri();
				return ResponseEntity.created(location).build();
			})
			.orElse(ResponseEntity.noContent().build());
	}


	private void validateUser(Long userId) throws UserNotFoundException {
		this.accountRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException(userId));
	}
}
