package com.smashpen.smashpen;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(Long userId) {
		super("Could not find user with id " + userId);
	}
}
