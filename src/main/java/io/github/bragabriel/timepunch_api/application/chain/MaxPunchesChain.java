package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.MaxPunchesExceededException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("maxPunchesChain")
public class MaxPunchesChain extends PunchClockChainHandler {

	@Override
	public void handle(User user, List<PunchClock> punches, LocalDateTime now) {
		if (punches.size() >= 4) {
			throw new MaxPunchesExceededException();
		}
		super.handle(user, punches, now);
	}
}
