package io.github.bragabriel.timepunch_api.domain.exception;

public class PunchClockNotAllowedOnWeekendException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "Work is not allowed on weekends.";

	public PunchClockNotAllowedOnWeekendException() {
		super(DEFAULT_MESSAGE);
	}
}
