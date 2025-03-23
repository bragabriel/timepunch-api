package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.MaxPunchesExceededException;
import io.github.bragabriel.timepunch_api.domain.exception.PunchClockNotAllowedOnWeekendException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class WeekendChain extends PunchClockChainHandler {

	@Override
	public void handle(User user, List<PunchClock> punches, LocalDateTime now) {
		//|| now.getDayOfWeek() == DayOfWeek.SUNDAY
		if (now.getDayOfWeek() == DayOfWeek.SATURDAY) {
			throw new PunchClockNotAllowedOnWeekendException();
		}
		super.handle(user, punches, now);
	}
}
