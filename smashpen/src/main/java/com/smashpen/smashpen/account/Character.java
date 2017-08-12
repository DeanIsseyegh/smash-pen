package com.smashpen.smashpen.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Character {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique=true)
	private String name;

	Character() {}

	public Character(String name) {
		this.name = name.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
