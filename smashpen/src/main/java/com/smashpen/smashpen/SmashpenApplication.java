package com.smashpen.smashpen;

import com.smashpen.smashpen.repository.UserRepository;
import com.smashpen.smashpen.repository.CharacterNotesRepository;
import com.smashpen.smashpen.repository.CharacterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmashpenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmashpenApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository,
						   CharacterNotesRepository characterNotesRepository,
						   CharacterRepository characterRepository) {
		/*SmashCharacter pika = new SmashCharacter("Pikachu");
		SmashCharacter mario = new SmashCharacter("Mario");
		SmashCharacter bayonetta = new SmashCharacter("Bayonetta");
		Stream.of(pika, mario, bayonetta).forEach(character -> characterRepository.saveAndFlush(character));
		return (evt) -> {
				Arrays.asList(
				"dean,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						username -> {
							User domain = userRepository.save(new User(username,
									"password"));
							characterNotesRepository.save(new CharacterNotes(domain, characterRepository.findOne(1L), "Pika fsmash vry stronk"));
							characterNotesRepository.save(new CharacterNotes(domain, characterRepository.findOne(2L), "Mario is pretty smelly"));
							characterNotesRepository.save(new CharacterNotes(domain, characterRepository.findOne(3L), "Bayo OP"));
						});
		};*/
		return null;
	}
}
