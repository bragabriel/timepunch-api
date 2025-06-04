package io.github.bragabriel.timepunch_api.application.dto;

import java.time.LocalDateTime;

public record PunchClockResponse(
		String name, LocalDateTime timePunch
) {
}
