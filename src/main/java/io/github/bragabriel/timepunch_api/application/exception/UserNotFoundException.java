package io.github.bragabriel.timepunch_api.application.exception;

public class UserNotFoundException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "User not found for id: %d";

	public UserNotFoundException(final Long userId) {
		super(String.format(DEFAULT_MESSAGE, userId));
	}

}
