package io.github.bragabriel.timepunch_api.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record WorkedHoursResponse(
		@Schema(description = "Total worked hours", example = "08:00") String totalWorkedHours
) {
}
