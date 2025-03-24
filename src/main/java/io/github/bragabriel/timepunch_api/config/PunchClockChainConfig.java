package io.github.bragabriel.timepunch_api.config;

import io.github.bragabriel.timepunch_api.application.chain.LunchBreakChain;
import io.github.bragabriel.timepunch_api.application.chain.MaxPunchesChain;
import io.github.bragabriel.timepunch_api.application.chain.PunchClockChainHandler;
import io.github.bragabriel.timepunch_api.application.chain.WeekendChain;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Punch Clock validation chain.
 *
 * This class defines the validation rules in a Chain of Responsibility pattern:
 * 1. No punch clocking on weekends (Saturday and Sunday).
 * 2. A maximum of 4 punches per day.
 * 3. A minimum 1-hour lunch break.
 *
 * These checks are linked in sequence, with each handler passing the request
 * to the next until all rules are validated.
 */
@Configuration
public class PunchClockChainConfig {

	/**
	 * Creates and configures the Punch Clock validation chain.
	 *
	 * This method is not intended for extension or overriding. It defines the
	 * sequence of validation checks, starting with the weekend check, followed by
	 * the max punches check, and finally the lunch break check.
	 *
	 * @return The first handler in the chain, which is the {@link WeekendChain}.
	 */
	@Bean
	@Qualifier("punchClockHandler")
	public PunchClockChainHandler punchClockHandler() {
		PunchClockChainHandler weekendHandler = new WeekendChain();
		PunchClockChainHandler maxPunchesHandler = new MaxPunchesChain();
		PunchClockChainHandler lunchBreakHandler = new LunchBreakChain();

		weekendHandler.setNext(maxPunchesHandler);
		maxPunchesHandler.setNext(lunchBreakHandler);

		return weekendHandler;
	}
}
