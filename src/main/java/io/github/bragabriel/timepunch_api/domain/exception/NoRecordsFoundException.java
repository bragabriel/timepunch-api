package io.github.bragabriel.timepunch_api.domain.exception;

import java.time.LocalDate;

public class NoRecordsFoundException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "No records found for user ID %d on date: %s";

	public NoRecordsFoundException(final long userId, final LocalDate date) {
		super(String.format(DEFAULT_MESSAGE, userId, date));
	}
}
