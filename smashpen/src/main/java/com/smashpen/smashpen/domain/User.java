package com.smashpen.smashpen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

	@OneToMany private Set<CharacterNotes> characterNotes = new HashSet<>();
	@Id @GeneratedValue private Long id;
	@JsonIgnore private String password;
	private String username;
	private Boolean enabled;

	public User(Boolean enabled, String name, String password) {
		this.enabled = enabled;
		this.username = name;
		this.password = password;
	}

	User() {}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}