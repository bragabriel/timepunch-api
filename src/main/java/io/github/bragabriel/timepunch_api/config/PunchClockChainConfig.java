package io.github.bragabriel.timepunch_api.config;

import io.github.bragabriel.timepunch_api.application.chain.LunchBreakChain;
import io.github.bragabriel.timepunch_api.application.chain.MaxPunchesChain;
import io.github.bragabriel.timepunch_api.application.chain.PunchClockChainHandler;
import io.github.bragabriel.timepunch_api.application.chain.WeekendChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PunchClockChainConfig {

	@Bean
	public PunchClockChainHandler punchClockHandler() {
		PunchClockChainHandler weekendHandler = new WeekendChain();
		PunchClockChainHandler maxPunchesHandler = new MaxPunchesChain();
		PunchClockChainHandler lunchBreakHandler = new LunchBreakChain();

		weekendHandler
				.setNext(maxPunchesHandler)
				.setNext(lunchBreakHandler);

		return weekendHandler;
	}
}
