package com.smashpen.smashpen.account;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface CharacterNotesRepository extends JpaRepository<CharacterNotes, Long> {
	Collection<CharacterNotes> findAllByAccountId(Long id);
	CharacterNotes findCharacterNotesByCharacterAndAccount(Character character, Account account);
}