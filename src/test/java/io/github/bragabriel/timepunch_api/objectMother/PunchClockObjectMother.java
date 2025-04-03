package io.github.bragabriel.timepunch_api.objectMother;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class PunchClockObjectMother {

	public static PunchClock createPunchClockWithUser(final User user) {
		return PunchClock.builder()
				.user(user)
				.punchTime(LocalDateTime.now())
				.build();
	}

	public static PunchClock createPunchClockWithUserAndIdAndPunchTime(final User user, final Long id,
																	   final LocalDateTime punchTime) {
		return PunchClock.builder()
				.id(id)
				.user(user)
				.punchTime(punchTime)
				.build();
	}
}
