package io.github.bragabriel.timepunch_api.application.controller;

import io.github.bragabriel.timepunch_api.application.dto.PunchClockResponse;
import io.github.bragabriel.timepunch_api.application.dto.WorkedHoursRequest;
import io.github.bragabriel.timepunch_api.application.dto.WorkedHoursResponse;
import io.github.bragabriel.timepunch_api.application.service.PunchClockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/punch-clock")
@AllArgsConstructor
@Tag(name = "Punch Clock", description = "Endpoints for punch clock management")
public class PunchClockController {

	private final PunchClockService service;

	@PostMapping("/{userId}")
	@Operation(summary = "Registers a work punch")
	public ResponseEntity<PunchClockResponse> registerPunchClock(@PathVariable("userId") final Long userId) {
		PunchClockResponse response = service.registerPunchClock(userId);
		return ResponseEntity.status(201).body(response);
	}

	@GetMapping()
	@Operation(summary = "Gets total worked hours for a given date and user")
	public ResponseEntity<WorkedHoursResponse> getWorkedHours(
			@RequestParam
			@Parameter(
					description = "User ID",
					required = true
			) final Long userId,
			@RequestParam
			@Parameter(
					description = "Date of the workday in format 'yyyy-MM-dd'",
					required = true,
					schema = @Schema(type = "string", format = "date", example = "2025-03-23")
			) final LocalDate date) {
		return ResponseEntity.ok(service.getWorkedHours(userId, date));
	}
}
