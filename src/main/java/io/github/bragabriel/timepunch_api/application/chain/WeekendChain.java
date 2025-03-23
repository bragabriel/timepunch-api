package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.InvalidPunchClockException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class WeekendChain extends PunchClockChainHandler {

	@Override
	public void handle(User user, List<PunchClock> punches, LocalDateTime now) {
		//|| now.getDayOfWeek() == DayOfWeek.SUNDAY
		if (now.getDayOfWeek() == DayOfWeek.SATURDAY) {
			throw new InvalidPunchClockException("Work is not allowed on weekends.");
		}
		super.handle(user, punches, now);
	}
}
