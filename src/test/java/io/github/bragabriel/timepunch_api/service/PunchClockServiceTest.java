package io.github.bragabriel.timepunch_api.service;

import io.github.bragabriel.timepunch_api.application.dto.WorkedHoursResponse;
import io.github.bragabriel.timepunch_api.application.exception.NoRecordsFoundException;
import io.github.bragabriel.timepunch_api.application.service.PunchClockService;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ALL")
@ActiveProfiles("test")
@SpringBootTest
class PunchClockServiceTest {

	@Autowired
	private PunchClockService punchClockService;

	@Autowired
	private PunchClockRepository punchClockRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldThrowNoRecordsFoundException_whenNoRecordsExistsForUserOnDate() {
		Long userId = 1L;
		LocalDate date = LocalDate.of(2025, 3, 23);
		String expectedMessage = "No records found for user ID 1 on date: 2025-03-23";

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> punchClockService.getWorkedHours(userId, date));

		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void shouldCalculateTimeDifferenceBetweenStartAndEnd() {
		User user = UserObjectMother.createUserWithoutId();
		var savedUser = userRepository.save(user);
		assertNotNull(savedUser.getId());

		LocalDate date = LocalDate.of(2025, 3, 23);

		PunchClock entry =
				PunchClockObjectMother.createPunchClockWithoutIdWithUserAndPunchTime(
						user, LocalDateTime.of(date, LocalTime.of(8, 0)));

		PunchClock exit =
				PunchClockObjectMother.createPunchClockWithoutIdWithUserAndPunchTime(
						user, LocalDateTime.of(date, LocalTime.of(12, 0)));

		var punchClockList = List.of(entry, exit);
		punchClockRepository.save(entry);
		punchClockRepository.save(exit);

		WorkedHoursResponse response = punchClockService.getWorkedHours(savedUser.getId(), date);

		assertEquals("04:00", response.totalWorkedHours());
	}

}

