package io.github.bragabriel.timepunch_api.repository;

import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void saveTest() {
		User user = UserObjectMother.createUser();
		var savedUser = userRepository.save(user);

		Assertions.assertNotNull(savedUser.getId());
		Assertions.assertEquals(user.getName(), savedUser.getName());
		Assertions.assertEquals(user.getPunchClocks(), savedUser.getPunchClocks());
	}

	@Test
	void findByIdTest() {
		User user = UserObjectMother.createUser();
		var savedUser = userRepository.save(user);

		var foundUser = userRepository.findById(savedUser.getId()).orElse(null);

		Assertions.assertNotNull(foundUser);
		Assertions.assertEquals(user.getId(), savedUser.getId());
		Assertions.assertEquals(user.getName(), savedUser.getName());
		Assertions.assertEquals(user.getPunchClocks(), savedUser.getPunchClocks());
	}
}
