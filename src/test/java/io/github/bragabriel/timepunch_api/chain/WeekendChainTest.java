package io.github.bragabriel.timepunch_api.chain;

import io.github.bragabriel.timepunch_api.application.chain.WeekendChain;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.PunchClockNotAllowedOnWeekendException;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WeekendChainTest {

	@Autowired
	private WeekendChain weekendChain;

	@Test
	void shouldThrowPunchClockNotAllowedOnWeekendException() {
		User user = UserObjectMother.createUserWithId();

		LocalDate today = LocalDate.now();
		LocalDate nextSaturday = today.with(DayOfWeek.SATURDAY);

		var weekend = LocalDateTime.of(2024, 12, nextSaturday.getDayOfMonth(), 9, 0);

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

		LocalDate today = LocalDate.now();
		LocalDate nextThursday = today.with(DayOfWeek.THURSDAY);

		var weekday = LocalDateTime.of(2024, 12, nextThursday.getDayOfMonth(), 9, 0);

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(user, 1L, weekday);

		var punches = Collections.singletonList(firstPunch);

		assertDoesNotThrow(() -> {
			weekendChain.handle(user, punches, weekday);
		});
	}
}
