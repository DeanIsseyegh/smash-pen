package com.smashpen.smashpen;

import com.smashpen.smashpen.repository.UserRepository;
import com.smashpen.smashpen.domain.SmashCharacter;
import com.smashpen.smashpen.domain.CharacterNotes;
import com.smashpen.smashpen.repository.CharacterNotesRepository;
import com.smashpen.smashpen.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/{userId}/character")
public class CharacterRestController {

	private UserRepository userRepository;
	private CharacterNotesRepository characterNotesRepository;
	private CharacterRepository characterRepository;

	@Autowired
	CharacterRestController(UserRepository userRepository,
							CharacterNotesRepository characterNotesRepository,
							CharacterRepository characterRepository) {
		this.userRepository = userRepository;
		this.characterNotesRepository = characterNotesRepository;
		this.characterRepository = characterRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<CharacterNotes> readCharacters(@PathVariable Long userId) throws UserNotFoundException {
		this.validateUser(userId);
		return this.characterNotesRepository.findAllByUserId(userId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{characterNotesId}")
	CharacterNotes readCharacter(@PathVariable Long userId, @PathVariable Long characterNotesId) throws UserNotFoundException {
		this.validateUser(userId);
		return this.characterNotesRepository.findOne(characterNotesId);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable Long userId, @RequestBody CharacterNotes input) throws UserNotFoundException {
		this.validateUser(userId);
		return this.userRepository.findById(userId).map(account -> {
				SmashCharacter smashCharacter = characterRepository.findByName(input.getSmashCharacter().getName());
				CharacterNotes existingNotes = characterNotesRepository.findCharacterNotesBySmashCharacterAndUser(smashCharacter, account);
				CharacterNotes result;
				if (existingNotes != null) {
					existingNotes.setNotes(input.getNotes());
					result = characterNotesRepository.save(existingNotes);
				} else {
					result = characterNotesRepository.save(new CharacterNotes(account, smashCharacter, input.getNotes()));
				}
				URI location = ServletUriComponentsBuilder
							.fromCurrentRequest().path("/{id}")
							.buildAndExpand(result.getId()).toUri();
				return ResponseEntity.created(location).build();
			})
			.orElse(ResponseEntity.noContent().build());
	}


	private void validateUser(Long userId) throws UserNotFoundException {
		this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Long currentUserId = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId();
		if (!userId.equals(currentUserId)) {
			throw new SecurityException("Cannot access another users account");
		}
	}
}
