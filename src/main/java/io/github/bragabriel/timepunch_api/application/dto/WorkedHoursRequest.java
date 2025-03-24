package io.github.bragabriel.timepunch_api.application.dto;

import java.time.LocalDate;

public record WorkedHoursRequest(
		Long userId, LocalDate date
) {
}
