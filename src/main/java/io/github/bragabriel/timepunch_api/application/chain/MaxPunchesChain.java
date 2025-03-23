package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.InvalidPunchClockException;

import java.time.LocalDateTime;
import java.util.List;

public class MaxPunchesChain extends PunchClockChainHandler {

	@Override
	public void handle(User user, List<PunchClock> punches, LocalDateTime now) {
		if (punches.size() >= 4) {
			throw new InvalidPunchClockException("Only 4 punches per day are allowed.");
		}
		super.handle(user, punches, now);
	}
}
