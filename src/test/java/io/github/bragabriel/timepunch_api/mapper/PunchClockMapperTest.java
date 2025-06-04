package io.github.bragabriel.timepunch_api.mapper;

import io.github.bragabriel.timepunch_api.application.dto.PunchClockResponse;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.mapper.PunchClockMapper;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class PunchClockMapperTest {

	@Autowired
	PunchClockMapper mapper;

	@Test
	public void shouldMapPunchClockToResponse(){
		// Arrange
		User user = UserObjectMother.createUserWithId();
		PunchClock punchClock = PunchClockObjectMother.createPunchClockWithUser(user);

		// Act
		PunchClockResponse response = mapper.punchClockToResponse(punchClock);

		// Assert
		Assertions.assertNotNull(response);
		Assertions.assertEquals(punchClock.getUser().getName(), response.name());
		Assertions.assertEquals(punchClock.getPunchTime(), response.timePunch());
	}

	@Test
	public void shouldMapResponseToPunchClock(){
		// Arrange
		PunchClockResponse punchClockResponse = new PunchClockResponse("Gabriel", LocalDateTime.now());

		// Act
		PunchClock punchClock = mapper.punchClockResponseToPunchClock(punchClockResponse);

		// Assert
		Assertions.assertNotNull(punchClock);
		Assertions.assertEquals(punchClockResponse.name(), punchClock.getUser().getName());
		Assertions.assertEquals(punchClockResponse.timePunch(), punchClock.getPunchTime());
	}
}
