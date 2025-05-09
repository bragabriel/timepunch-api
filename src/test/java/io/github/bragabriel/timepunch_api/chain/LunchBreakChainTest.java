package io.github.bragabriel.timepunch_api.chain;

import io.github.bragabriel.timepunch_api.application.chain.LunchBreakChain;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.application.exception.InvalidLunchBreakException;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class LunchBreakChainTest {

	@Autowired
	private LunchBreakChain lunchBreakChain;

	@Test
	void shouldThrowInvalidLunchBreakException_whenLunchBreakIsLessThanOneHour() {
		User user = UserObjectMother.createUserWithId();
		var date = LocalDate.now();

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 1L, LocalDateTime.of(date, LocalTime.of(9, 0)));

		PunchClock secondPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 2L, LocalDateTime.of(date, LocalTime.of(12, 0)));

		var thirdPunch = LocalDateTime.of(date, LocalTime.of(12, 30));

		var punches = Arrays.asList(firstPunch, secondPunch);

		assertThrows(InvalidLunchBreakException.class, () -> {
			lunchBreakChain.handle(user, punches, thirdPunch);
		});
	}

	@Test
	void shouldNotThrowInvalidLunchBreakException_whenLunchBreakIsMoreThanOneHour() {
		User user = UserObjectMother.createUserWithoutId();
		var date = LocalDate.now();

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 1L, LocalDateTime.of(date, LocalTime.of(9, 0)));

		PunchClock secondPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 2L, LocalDateTime.of(date, LocalTime.of(12, 0)));

		var punches = Arrays.asList(firstPunch, secondPunch);

		var thirdPunch = LocalDateTime.of(date, LocalTime.of(13, 0));

		assertDoesNotThrow(() -> {
			lunchBreakChain.handle(user, punches, thirdPunch);
		});
	}
}
