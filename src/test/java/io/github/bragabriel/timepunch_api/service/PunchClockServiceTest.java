package io.github.bragabriel.timepunch_api.service;

import io.github.bragabriel.timepunch_api.application.dto.WorkedHoursResponse;
import io.github.bragabriel.timepunch_api.application.service.PunchClockService;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.NoRecordsFoundException;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SuppressWarnings("ALL")
@SpringBootTest
class PunchClockServiceTest {

	@Autowired
	private PunchClockService punchClockService;

	@MockitoBean
	private PunchClockRepository punchClockRepository;

	@Test
	void shouldThrowNoRecordsFoundException_whenNoRecordsExistsForUserOnDate() {
		Long userId = 1L;
		LocalDate date = LocalDate.of(2025, 3, 23);
		String expectedMessage = "No records found for user ID 1 on date: 2025-03-23";

		when(punchClockRepository.findByUserIdAndPunchDate(userId, date))
				.thenReturn(Collections.emptyList());

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> punchClockService.getWorkedHours(userId, date));

		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void shouldCalculateTimeDifferenceBetweenStartAndEnd() {
		User user = UserObjectMother.createUserWithId();
		LocalDate date = LocalDate.of(2025, 3, 23);

		PunchClock entry =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(user, 1L,
						LocalDateTime.of(2025, 3, 23, 8, 0));

		PunchClock exit =
				PunchClockObjectMother.createPunchClockWithUserAndIdAndPunchTime(user, 1L,
						LocalDateTime.of(2025, 3, 23, 12, 0));

		var userId = user.getId();
		when(punchClockRepository.findByUserIdAndPunchDate(userId, date)).thenReturn(List.of(entry, exit));

		WorkedHoursResponse response = punchClockService.getWorkedHours(userId, date);

		assertEquals("04:00", response.totalWorkedHours());
	}

}

