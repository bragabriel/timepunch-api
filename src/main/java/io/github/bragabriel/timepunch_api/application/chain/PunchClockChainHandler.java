package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public abstract class PunchClockChainHandler {

	protected PunchClockChainHandler nextHandler;

	public PunchClockChainHandler setNext(PunchClockChainHandler nextHandler) {
		this.nextHandler = nextHandler;
		return nextHandler;
	}

	public void handle(final User user, final List<PunchClock> punches, final LocalDateTime now) {
		if (nextHandler != null) {
			nextHandler.handle(user, punches, now);
		}
	}
}
