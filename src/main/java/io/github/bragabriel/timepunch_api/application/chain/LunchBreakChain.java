package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.application.exception.InvalidLunchBreakException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("lunchBreakChain")
public class LunchBreakChain extends PunchClockChainHandler {

	private static final long MIN_LUNCH_BREAK_DURATION_MINUTES = 60;

	@Override
	public final void handle(final User user, final List<PunchClock> punches, final LocalDateTime now) {
		if (punches.size() == 2) {
			LocalDateTime lunchStart = punches.get(1).getPunchTime();
			if (Duration.between(lunchStart, now).toMinutes() < MIN_LUNCH_BREAK_DURATION_MINUTES) {
				throw new InvalidLunchBreakException();
			}
		}
		super.handle(user, punches, now);
	}
}
