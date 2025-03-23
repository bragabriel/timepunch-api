package io.github.bragabriel.timepunch_api.domain.exception;

public class InvalidPunchClockException extends RuntimeException {
	public InvalidPunchClockException(final String message) {
		super(message);
	}
}
