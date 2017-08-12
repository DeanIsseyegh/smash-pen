package com.smashpen.smashpen;

import com.smashpen.smashpen.account.*;
import com.smashpen.smashpen.account.Character;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class SmashpenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmashpenApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
						   CharacterNotesRepository characterNotesRepository,
						   CharacterRepository characterRepository) {
		Character pika = new Character("Pikachu");
		Character mario = new Character("Mario");
		Character bayonetta = new Character("Bayonetta");
		Stream.of(pika, mario, bayonetta).forEach(character -> characterRepository.saveAndFlush(character));
		return (evt) -> {
				Arrays.asList(
				"jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						username -> {
							Account account = accountRepository.save(new Account(username,
									"password"));
							characterNotesRepository.save(new CharacterNotes(account, characterRepository.findOne(1L), "Pika fsmash vry stronk"));
							characterNotesRepository.save(new CharacterNotes(account, characterRepository.findOne(2L), "Mario is pretty smelly"));
							characterNotesRepository.save(new CharacterNotes(account, characterRepository.findOne(3L), "Bayo OP"));
						});
		};
	}
}
