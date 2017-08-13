package com.smashpen.smashpen.repository;

import com.smashpen.smashpen.domain.SmashCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<SmashCharacter, Long> {
	SmashCharacter findByName(String name);
}