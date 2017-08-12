package com.smashpen.smashpen.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	Character findByName(String name);
}