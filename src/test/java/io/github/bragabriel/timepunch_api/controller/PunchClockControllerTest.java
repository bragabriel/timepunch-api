package io.github.bragabriel.timepunch_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import io.github.bragabriel.timepunch_api.objectMother.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PunchClockControllerTest {

	@Value("${spring.application.base-url}")
	private String baseUrl;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PunchClockRepository punchClockRepository;

	@Autowired
	UserRepository userRepository;

	@BeforeEach
	void setUp() {
		punchClockRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void shouldRegisterPunchClock() throws Exception {
		User user = userRepository.save(UserObjectMother.createUserWithoutId());

		var request = user.getId();

		mockMvc.perform(post(String.format("%s/punch-clock/%s", baseUrl, request))
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isCreated());
	}

	@Test
	void shouldNotUserAndReturnNotFoundException() throws Exception {
		var request = 1L;
		mockMvc.perform(post(String.format("%s/punch-clock/%s", baseUrl, request))
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isNotFound())
				.andReturn();
	}

}
