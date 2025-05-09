package io.github.bragabriel.timepunch_api.chain;

import io.github.bragabriel.timepunch_api.application.chain.WeekendChain;
import io.github.bragabriel.timepunch_api.application.exception.PunchClockNotAllowedOnWeekendException;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class WeekendChainTest {

	@Autowired
	private WeekendChain weekendChain;

	@Test
	void shouldThrowPunchClockNotAllowedOnWeekendException() {
		User user = UserObjectMother.createUserWithId();

		LocalDateTime weekend = LocalDateTime.of(2024, 12, 7, 9, 0);

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(user, 1L, weekend);

		var punches = Arrays.asList(firstPunch, firstPunch);

		assertThrows(PunchClockNotAllowedOnWeekendException.class, () -> {
			weekendChain.handle(user, punches, weekend);
		});
	}

	@Test
	void shouldNotThrowException_whenItIsNotWeekend() {
		User user = UserObjectMother.createUserWithId();

		LocalDateTime weekday = LocalDateTime.of(2024, 12, 5, 9, 0);

		PunchClock firstPunch = PunchClockObjectMother
				.createPunchClockWithUserAndIdAndPunchTime(user, 1L, weekday);

		var punches = Collections.singletonList(firstPunch);

		assertDoesNotThrow(() -> {
			weekendChain.handle(user, punches, weekday);
		});
	}
}
