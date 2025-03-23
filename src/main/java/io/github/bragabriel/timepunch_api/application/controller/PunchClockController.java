package io.github.bragabriel.timepunch_api.application.controller;

import io.github.bragabriel.timepunch_api.application.service.PunchClockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/punch-clock")
@AllArgsConstructor
@Tag(name = "Punch Clock", description = "Endpoints for punch clock management")
public class PunchClockController {

	private final PunchClockService service;

	@PostMapping("/{userId}")
	@Operation(summary = "Registers a work punch")
	public ResponseEntity<Void> registerPunchClock(@PathVariable("userId") Long userId) {
		service.registerPunchClock(userId);
		return ResponseEntity.status(201).build();
	}
}
