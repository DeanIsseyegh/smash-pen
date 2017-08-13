package com.smashpen.smashpen.repository;

import com.smashpen.smashpen.domain.User;
import com.smashpen.smashpen.domain.SmashCharacter;
import com.smashpen.smashpen.domain.CharacterNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface CharacterNotesRepository extends JpaRepository<CharacterNotes, Long> {
	Collection<CharacterNotes> findAllByUserId(Long id);
	CharacterNotes findCharacterNotesBySmashCharacterAndUser(SmashCharacter smashCharacter, User user);
}