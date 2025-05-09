package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.application.exception.MaxPunchesExceededException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("maxPunchesChain")
public class MaxPunchesChain extends PunchClockChainHandler {

	private static final int MAX_PUNCHES_ALLOWED = 4;

	@Override
	public final void handle(final User user, final List<PunchClock> punches, final LocalDateTime now) {
		if (punches.size() >= MAX_PUNCHES_ALLOWED) {
			throw new MaxPunchesExceededException();
		}
		super.handle(user, punches, now);
	}
}
