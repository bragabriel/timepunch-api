package io.github.bragabriel.timepunch_api.chain;

import io.github.bragabriel.timepunch_api.application.chain.MaxPunchesChain;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.application.exception.MaxPunchesExceededException;
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
class MaxPunchChainTest {

	@Autowired
	private MaxPunchesChain maxPunchesChain;

	@Test
	void shouldThrowMaxPunchesExceededException_whenMaxPunchesExceedFour() {
		User user = UserObjectMother.createUserWithId();
		var date = LocalDate.of(2025, 2, 5);
		var now = LocalDateTime.of(date, LocalTime.now());

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 1L, LocalDateTime.of(date, LocalTime.of(9, 0)));

		PunchClock secondPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 2L, LocalDateTime.of(date, LocalTime.of(12, 0)));

		PunchClock thirdPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 3L, LocalDateTime.of(date, LocalTime.of(14, 0)));

		PunchClock fourthPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 4L, LocalDateTime.of(date, LocalTime.of(18, 0)));

		var punches = Arrays.asList(firstPunch, secondPunch, thirdPunch, fourthPunch);

		assertThrows(MaxPunchesExceededException.class, () -> {
			maxPunchesChain.handle(user, punches, now);
		});
	}

	@Test
	void shouldNotThrowMaxPunchesExceededException_whenMaxPunchesAreLessThanFour() {
		User user = UserObjectMother.createUserWithId();
		var date = LocalDate.of(2025, 2, 5);
		var now = LocalDateTime.of(date, LocalTime.now());

		PunchClock firstPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 1L, LocalDateTime.of(date, LocalTime.of(9, 0)));

		PunchClock secondPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 2L, LocalDateTime.of(date, LocalTime.of(12, 0)));

		PunchClock thirdPunch =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(
						user, 3L, LocalDateTime.of(date, LocalTime.of(14, 0)));

		var punches = Arrays.asList(firstPunch, secondPunch, thirdPunch);

		assertDoesNotThrow(() -> {
			maxPunchesChain.handle(user, punches, now);
		});
	}

}
