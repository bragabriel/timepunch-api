package io.github.bragabriel.timepunch_api.objectMother;

import io.github.bragabriel.timepunch_api.domain.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserObjectMother {

	public static User createUserWithoutId() {
		return User.builder()
				.name("Gabriel")
				.build();
	}

	public static User createUserWithId() {
		return User.builder()
				.id(1L)
				.name("Gabriel")
				.build();
	}

}
