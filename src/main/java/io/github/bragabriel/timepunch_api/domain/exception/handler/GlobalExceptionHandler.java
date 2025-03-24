package io.github.bragabriel.timepunch_api.domain.exception.handler;

import io.github.bragabriel.timepunch_api.application.controller.PunchClockController;
import io.github.bragabriel.timepunch_api.domain.exception.InvalidLunchBreakException;
import io.github.bragabriel.timepunch_api.domain.exception.MaxPunchesExceededException;
import io.github.bragabriel.timepunch_api.domain.exception.NoRecordsFoundException;
import io.github.bragabriel.timepunch_api.domain.exception.PunchClockNotAllowedOnWeekendException;
import io.github.bragabriel.timepunch_api.domain.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PunchClockController.class})
public class GlobalExceptionHandler {

	/**
	 * Handles InvalidLunchBreakException and returns a 400 Bad Request status with the exception message.
	 *
	 * @param ex the exception thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(InvalidLunchBreakException.class)
	@ApiResponse(responseCode = "400",
			description = "Bad request, lunch break must be at least 1 hour",
			content = @Content(schema = @Schema(type = "string", example = "Lunch break must be at least 1 hour.")))
	public ResponseEntity<String> handleInvalidLunchBreakException(final InvalidLunchBreakException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	/**
	 * Handles MaxPunchesExceededException and returns a 400 Bad Request status with the exception message.
	 *
	 * @param ex the exception thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(MaxPunchesExceededException.class)
	@ApiResponse(responseCode = "400",
			description = "Bad request, only 4 punches per day are allowed",
			content = @Content(schema = @Schema(type = "string", example = "Only 4 punches per day are allowed.")))
	public ResponseEntity<String> handleMaxPunchesExceededException(final MaxPunchesExceededException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	/**
	 * Handles PunchClockNotAllowedOnWeekendException and returns a 400 Bad Request status with the exception message.
	 *
	 * @param ex the exception thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(PunchClockNotAllowedOnWeekendException.class)
	@ApiResponse(responseCode = "400",
			description = "Bad request, work is not allowed on weekends",
			content = @Content(schema = @Schema(type = "string", example = "Work is not allowed on weekends.")))
	public ResponseEntity<String> handlePunchClockNotAllowedOnWeekendException(
			final PunchClockNotAllowedOnWeekendException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	/**
	 * Handles NoRecordsFoundException and returns a 404 Not Found status with the exception message.
	 *
	 * @param ex the exception thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(NoRecordsFoundException.class)
	@ApiResponse(responseCode = "404",
			description = "Not found, no records found for the specified date and user",
			content = @Content(
					schema = @Schema(
							type = "string",
							example = "No records found for user ID 123 on date: 2024-12-01"))
	)
	public ResponseEntity<String> handleNoRecordsFoundException(
			final NoRecordsFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Handles UserNotFoundException and returns a 404 Not Found status with the exception message.
	 *
	 * @param ex the exception thrown
	 * @return ResponseEntity containing the error message and HTTP status
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ApiResponse(responseCode = "404",
			description = "Not found, user not found for the specified ID",
			content = @Content(
					schema = @Schema(
							type = "string",
							example = "User not found for ID: 123"))
	)
	public ResponseEntity<String> handleUserNotFoundException(
			final UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
