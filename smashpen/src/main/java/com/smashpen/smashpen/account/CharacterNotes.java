package com.smashpen.smashpen.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class CharacterNotes {

	@JsonIgnore
	@ManyToOne
	private Account account;
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private Character character;
	private String notes;

	CharacterNotes() {}

	public CharacterNotes(Account account, Character character, String notes) {
		this.character = character;
		this.notes = notes;
		this.account = account;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
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
