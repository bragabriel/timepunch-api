package io.github.bragabriel.timepunch_api.application.chain;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.application.exception.PunchClockNotAllowedOnWeekendException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("weekendChain")
public class WeekendChain extends PunchClockChainHandler {

	@Override
	public final void handle(final User user, final List<PunchClock> punches, final LocalDateTime now) {
		if (now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY) {
			throw new PunchClockNotAllowedOnWeekendException();
		}
		super.handle(user, punches, now);
	}
}
