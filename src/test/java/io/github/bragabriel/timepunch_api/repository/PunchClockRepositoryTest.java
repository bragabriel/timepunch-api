package io.github.bragabriel.timepunch_api.repository;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import io.github.bragabriel.timepunch_api.objectMother.PunchClockObjectMother;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class PunchClockRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PunchClockRepository punchClockRepository;

	@Test
	void saveTest() {
		User user = creteAndSaveUser();

		PunchClock punchClock = PunchClockObjectMother.createPunchClockWithUser(user);

		var savedPunchClock = punchClockRepository.save(punchClock);

		Assertions.assertNotNull(savedPunchClock.getId());
		Assertions.assertEquals(punchClock.getUser(), savedPunchClock.getUser());
		Assertions.assertEquals(punchClock.getPunchTime(), savedPunchClock.getPunchTime());
	}

	@Test
	@Transactional
	void findByUserIdAndPunchDate() {
		User user = creteAndSaveUser();

		PunchClock punchClock = PunchClockObjectMother.createPunchClockWithUser(user);

		punchClockRepository.save(punchClock);

		var foundPunchTime =
				punchClockRepository.findByUserIdAndPunchDate(user.getId(), punchClock.getPunchTime().toLocalDate());

		Assertions.assertNotNull(foundPunchTime);
		Assertions.assertEquals(punchClock.getUser(), foundPunchTime.getFirst().getUser());
		Assertions.assertEquals(punchClock.getPunchTime(), foundPunchTime.getFirst().getPunchTime());
	}

	private User creteAndSaveUser() {
		User user = UserObjectMother.createUser();
		userRepository.save(user);
		return user;
	}
}
