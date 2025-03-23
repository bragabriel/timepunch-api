package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.InvalidPunchClockException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class LunchBreakChain extends PunchClockChainHandler {

	@Override
	public void handle(User user, List<PunchClock> punches, LocalDateTime now) {
		if (punches.size() == 2) {
			LocalDateTime lunchStart = punches.get(1).getPunchTime();
			if (Duration.between(lunchStart, now).toMinutes() < 60) {
				throw new InvalidPunchClockException("Lunch break must be at least 1 hour.");
			}
		}
		super.handle(user, punches, now);
	}
}
