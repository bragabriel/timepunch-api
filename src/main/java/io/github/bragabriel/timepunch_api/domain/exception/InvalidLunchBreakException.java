package io.github.bragabriel.timepunch_api.domain.exception;

public class InvalidLunchBreakException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "Lunch break must be at least 1 hour.";

	public InvalidLunchBreakException() {
		super(DEFAULT_MESSAGE);
	}
}
