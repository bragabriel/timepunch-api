package io.github.bragabriel.timepunch_api.application.exception;

public class MaxPunchesExceededException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "Only 4 punches per day are allowed.";

	public MaxPunchesExceededException() {
		super(DEFAULT_MESSAGE);
	}
}
