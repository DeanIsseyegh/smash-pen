package com.smashpen.smashpen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class CharacterNotes {

	@JsonIgnore @ManyToOne private User user;
	@Id @GeneratedValue private Long id;
	@ManyToOne private SmashCharacter smashCharacter;
	private String notes;

	CharacterNotes() {}

	public CharacterNotes(User user, SmashCharacter smashCharacter, String notes) {
		this.smashCharacter = smashCharacter;
		this.notes = notes;
		this.user = user;
	}

	public SmashCharacter getSmashCharacter() {
		return smashCharacter;
	}

	public void setSmashCharacter(SmashCharacter smashCharacter) {
		this.smashCharacter = smashCharacter;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}
}
